# Inventory Management System

![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![License](https://img.shields.io/badge/license-MIT-green)

A robust, secure inventory management system built with Spring Boot and React. This application provides role-based access control, complete inventory tracking, and a responsive front-end interface.

## 🚀 Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (Admin, Manager, Staff)
  - Secure password handling

- **Inventory Management**
  - Product tracking and categorization
  - Stock level monitoring and alerts
  - Batch operations for inventory updates

- **Reporting**
  - Inventory status reports
  - Transaction history
  - Stock movement analytics

- **Modern UI**
  - Responsive design
  - Real-time updates
  - Intuitive user interface

## 🔧 Technology Stack

### Backend
- **Spring Boot** - Core framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access and ORM
- **PostgreSQL** - Database
- **JWT** - Stateless authentication
- **Spring Validation** - Input validation
- **Lombok** - Boilerplate code reduction
- **JUnit & Mockito** - Testing

### Frontend
- **React** - UI library
- **Vite** - Build tool
- **Redux** - State management
- **Axios** - API communication
- **React Router** - Navigation
- **Tailwind CSS** - Styling

## 📋 Prerequisites

- JDK 17 or higher
- Maven 3.8+
- PostgreSQL 14+
- Node.js 18+
- npm or yarn

## 🔌 Installation & Setup

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone git@github.com:abdulbasit0-UI/inventory-management-spring-boot.git
   cd inventory-management-system
   ```

2. **Configure database**
   - Create a PostgreSQL database
   - Update `application.properties` or `application.yml` with your database credentials

3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

### Frontend Setup

1. **Clone the frontend repository**
   ```bash
   git clone git@github.com:abdulbasit0-UI/spring-react-frontend.git
   cd inventory-management-frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   # or
   yarn
   ```

3. **Run development server**
   ```bash
   npm run dev
   # or
   yarn dev
   ```
   The frontend will be available at `http://localhost:5173`

## 🔒 Environment Variables

Create a `.env` file in the root directory with the following variables:

```
# Backend
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/inventory_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword
JWT_SECRET=your_very_secure_jwt_secret_key
JWT_EXPIRATION_MS=86400000

```

## 🏗️ Project Structure

### Backend Structure

```
src/
├── main/
│   ├── java/com/yourusername/inventory/
│   │   ├── config/           # Configuration classes
│   │   ├── Category/       # Category Module
│   │   ├── Supplier/              # Supplier Module
│   │   ├── exception/        #  Exceptions
│   │   ├── User/            # User Module
│   │   ├── Order/       # Order Module
│   │   ├── security/         # Security configuration
│   │   └── InventoryManagementApplication.java
│   └── resources/
│       ├── application.yml   # Application properties
│       └── data.sql          # Initial data
└── test/                     # Unit and integration tests
```

### Frontend Structure

```
src/
├── assets/         # Static files
├── components/     # Reusable components
├── context/        # React context providers
├── hooks/          # Custom hooks
├── pages/          # Application pages
├── services/       # API service layer
├── store/          # Redux store
├── utils/          # Helper functions
└── App.jsx         # Root component
```


Key endpoints:

- **Authentication**: `/api/v1/cauth/**`
- **Users**: `/api/v1/users/**`
- **Products**: `/api/v1/products/**`
- **Categories**: `/api/v1/categories/**`
- **Inventory**: `/api/v1/suppliers/**`
- **Reports**: `/api/v1/orders/**`





## 🚀 Deployment

### Backend Deployment

The application can be deployed as a JAR file:

```bash
mvn clean package
java -jar target/inventory-management-1.0.0.jar
```

## 🛣️ Roadmap

- [ ] Advanced reporting and analytics dashboard
- [ ] Mobile application integration
- [ ] Multi-tenant support
- [ ] Barcode/QR code scanner integration
- [ ] Supplier management module

## 👥 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

Your Name - [your.email@example.com](mailto:habdulbasit51@gmail.com)

Frontend Repository: [https://github.com/abdulbasit0-UI/spring-react-frontend](https://github.com/abdulbasit0-UI/spring-react-frontend)

Project Link: [https://github.com/abdulbasit0-UI/inventory-management-spring-boot](https://github.com/abdulbasit0-UI/inventory-management-spring-boot)
