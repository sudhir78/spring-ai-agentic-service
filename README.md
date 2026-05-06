# Spring AI Chatbot

Spring Boot chatbot service using Spring AI and OpenAI, with REST and streaming chat endpoints.

## Features

- Plain text chat endpoint for simple request/response flows.
- Structured response endpoint with per-session conversation memory.
- Streaming response endpoint (`Flux<String>`) for incremental AI output.
- Layered design (`controller` -> `service` -> `Spring AI ChatClient`).
- Configurable model and temperature through application properties.

## Tech Stack

- Java 25
- Spring Boot 3.5.14
- Spring AI OpenAI starter
- Spring Web + WebFlux
- Maven
- Lombok

## Prerequisites

- JDK 25
- Maven 3.8+
- OpenAI API key

## Quick Start

1. Clone the repository.
2. Configure your OpenAI API key (recommended through environment variable).
3. Run the application.

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run
```

The service runs on `http://localhost:9090`.

## Configuration

Current core properties in `src/main/resources/application.properties`:

```properties
server.port=9090
spring.application.name=spring-ai-chatbot
spring.ai.openai.chat.options.model=gpt-4-turbo
spring.ai.openai.chat.options.temperature=0.7
```

For local development, prefer setting the API key via environment variable:

```powershell
$env:OPENAI_API_KEY="your-openai-api-key"
```

Then reference it from properties:

```properties
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

## API Endpoints

Base path: `/api/chat`

### 1) Basic Chat

- **Method:** `POST`
- **Path:** `/api/chat`
- **Request:**

```json
{
  "message": "Hello!"
}
```

- **Response:** plain text

### 2) Structured Chat (Session Memory)

- **Method:** `POST`
- **Path:** `/api/chat/chatWithStructure`
- **Request:**

```json
{
  "message": "Summarize this in one sentence."
}
```

- **Response:**

```json
{
  "response": "..."
}
```

This endpoint uses the HTTP session id as the conversation id for chat memory.

### 3) Streaming Chat (Session Memory)

- **Method:** `POST`
- **Path:** `/api/chat/streamchat`
- **Response type:** stream (`Flux<String>`)

This endpoint also uses the HTTP session id as the conversation id.

## Project Structure

```text
src/main/java/com/sudhir/springai/chatbot/
  controller/   # REST endpoints
  dto/          # Request and response models
  service/      # Chat orchestration and Spring AI calls
```

## Notes

- CORS is currently open (`@CrossOrigin("*")`) for easier client integration.
- For production, restrict CORS and avoid storing secrets directly in source-controlled files.
