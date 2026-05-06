# Spring AI Chatbot

A Spring Boot application that provides an AI-powered chat interface using OpenAI's GPT models through Spring AI.

## Features

- RESTful API endpoint for chat functionality
- Integration with OpenAI's GPT models via Spring AI
- Simple, clean architecture with controller-service-dto pattern
- Cross-origin resource sharing (CORS) enabled for web client integration
- Configurable AI model and parameters

## Architecture at a Glance

- **Controller layer** (`ChatController`) handles HTTP request/response mapping.
- **Service layer** (`ChatService`) contains chat business logic and AI orchestration.
- **DTO layer** (`ChatRequest`, `ChatResponse`) carries API payloads.
- **AI integration** uses Spring AI `ChatClient` with chat memory advisors.

## Technologies Used

- **Spring Boot 3.5.14** - Modern Java framework for building production-ready applications
- **Spring AI** - Framework for building AI-powered applications with Spring
- **OpenAI Integration** - Connects to OpenAI's GPT models
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management

## Prerequisites

- Java 25+ (JDK 25)
- Maven 3.8+
- OpenAI API key (required for authentication)

## Setup and Installation

### 1. Clone the Repository

```bash
# Clone the repository
git clone https://github.com/your-username/spring-ai-chatbot.git
cd spring-ai-chatbot
```

### 2. Configure OpenAI API Key

Set your OpenAI API key as an environment variable:

```bash
# Windows PowerShell
$env:OPENAI_API_KEY="your-openai-api-key-here"
```

Or add it to your `application.properties` file:

```properties
spring.ai.openai.api-key=your-openai-api-key-here
```

### 3. Build the Application

```bash
# Build with Maven
mvnw clean package
```

### 4. Run the Application

```bash
# Run the application
mvnw spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## API Documentation

### Chat Endpoints

**POST** `/api/chat` (Basic Chat)

Send a chat message to the AI model and receive plain text response.

**Request Body:**
```json
{
  "message": "Hello, how are you today?"
}
```

**Response:**
```text
I'm doing well, thank you for asking! How can I help you today?
```

**Curl Example:**
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello, how are you today?"}'
```

**POST** `/api/chat/chatWithStructure` (Structured Chat with Memory)

Send a chat message with system prompt and conversation memory, and receive structured JSON response with `response` field.

**Request Body:**
```json
{
  "message": "Hello, how are you today?"
}
```

**Response:**
```json
{
  "response": "I'm doing well, thank you for asking! How can I help you today?"
}
```

**Curl Example:**
```bash
curl -X POST http://localhost:8080/api/chat/chatWithStructure \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello, how are you today?"}'
```

## Configuration

The application is configured in `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=spring-ai-chatbot

# OpenAI API configuration
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4-turbo
spring.ai.openai.chat.options.temperature=0.7
```

## Project Structure

```
src/
├── main/
│   ├── java/com/sudhir/springai/chatbot/
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Data transfer objects
│   │   ├── service/         # Business logic services
│   │   └── SpringAiChatbotApplication.java  # Main application class
│   └── resources/
│       └── application.properties  # Configuration properties
└── test/  # Test classes
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## Acknowledgments

- Spring Boot Team
- Spring AI Team
- OpenAI Team
