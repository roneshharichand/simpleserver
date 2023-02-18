# Building the JAR Files from IntelliJ IDEA

1. Open your Java project in IntelliJ IDEA.
2. Click the Build menu item then click build artifacts

# Building and Running the Docker Image

To build and run a Docker image, you need to follow these steps:

## 1. Building the Docker image

To build the Docker image, run the following command in the root directory of your project (where your Dockerfile is located):

`docker build -t directory-listing .`


## 2. Running the Docker image

To run the Docker image, execute the following command:

`docker run -p 8080:8080 directory-listing`


## 3. Endpoints

After running the Docker image, you can access the following endpoints:

- Access root directory of Docker container:  http://localhost:8080/directory?path=/
- Health check:  http://localhost:8080/actuator/health

