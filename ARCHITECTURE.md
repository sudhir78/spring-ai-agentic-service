# Spring AI Chatbot Architecture

This document describes the architecture and request flow of the Spring AI Chatbot application.

## System Overview

The system is a Spring Boot REST service that routes chat requests to OpenAI through Spring AI `ChatClient`. It follows a layered structure with clear separation between API, service orchestration, and AI integration.

## Architecture Diagram

```mermaid
graph TD
    U[Client: Web / Mobile / CLI] -->|POST /api/chat/*| C[ChatController]
    C --> S[ChatService]
    S --> AI[Spring AI ChatClient]
    AI --> O[OpenAI API]

    C -. request model .-> RQ[ChatRequest]
    C -. structured response .-> RS[ChatResponse]
    C -. session id .-> SID[HttpSession.getId()]
    SID --> S
```

## Component Details

### 1. User Interface Layer
- **Web Client / Mobile App / CLI**: Any client that consumes the REST API
- Communicates using HTTP POST requests to `/api/chat`, `/api/chat/chatWithStructure`, and `/api/chat/streamchat`

### 2. Controller Layer
- **ChatController**: Handles incoming HTTP requests
  - Base mapping: `/api/chat`
  - Uses `@RestController` annotation
  - Enables CORS with `@CrossOrigin("*")`
  - Endpoint `POST /api/chat` returns plain text
  - Endpoint `POST /api/chat/chatWithStructure` returns `ChatResponse`
  - Endpoint `POST /api/chat/streamchat` returns `Flux<String>`
  - Uses `HttpSession` to derive `conversationId` for memory-enabled endpoints
  - Delegates AI/business logic to `ChatService`

### 3. Service Layer
- **ChatService**: Encapsulates AI interaction logic
  - Builds a `ChatClient` using `MessageChatMemoryAdvisor`
  - Uses `chat(message)` for simple one-call text response
  - Uses `chatWithStructure(message, conversationId)` for structured response + memory context
  - Uses `streamChat(message, conversationId)` for reactive streamed response

### 4. Data Transfer Object (DTO) Layer
- **ChatRequest**: Simple POJO for request data transfer
  - Contains `message` field for user input
  - Used for JSON serialization/deserialization
- **ChatResponse**: Structured response DTO
  - Contains `response` field for AI-generated content
  - Used for structured API responses

### 5. AI Integration Layer
- **ChatClient**: Spring AI's high-level client for LLM interactions
  - Configured via Spring Boot auto-configuration
  - Handles communication with OpenAI API
  - Provides fluent API for prompt building and execution
  - Supports system prompts, user messages, memory advisors, and streaming (`.stream().content()`)

### 6. External Dependency
- **OpenAI API**: External service providing language model capabilities
  - Accessed via Spring AI's OpenAI integration
  - Configured with API key and model parameters
  - Returns AI-generated responses

## Data Flow

1. **Client Request**: Client sends `POST` request with `ChatRequest`.
2. **Controller Handling**: `ChatController` reads `message`; for memory endpoints it also gets session id.
3. **Service Orchestration**: `ChatService` builds prompt, injects system text, and adds memory conversation id where needed.
4. **LLM Call**: `ChatClient` invokes OpenAI model.
5. **Return Path**: Response is returned as plain text, structured JSON, or stream, based on endpoint.

## Configuration

The application is configured in `application.properties`:

- `server.port`: Service port (`9090`)
- `spring.ai.openai.api-key`: Authentication key for OpenAI API
- `spring.ai.openai.chat.options.model`: Specifies GPT model (gpt-4-turbo)
- `spring.ai.openai.chat.options.temperature`: Controls response creativity (0.7)

## Technology Stack

- **Framework**: Spring Boot 3.5.14
- **AI Integration**: Spring AI with OpenAI starter
- **Build Tool**: Maven
- **Language**: Java 25
- **Code Generation**: Lombok for boilerplate reduction

## Security Considerations

- API key is configured via environment variable for security
- CORS is currently open and should be restricted in production
- API key should not be committed in plaintext properties
- Input validation/rate limiting should be added for production usage

## Scalability Considerations

- Stateless API design can be horizontally scaled
- Streaming endpoint supports low-latency UX for longer responses
- Can be containerized for deployment and scaling
