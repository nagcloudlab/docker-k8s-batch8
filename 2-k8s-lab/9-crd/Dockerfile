# Use official Python base image
FROM python:3.10-slim

# Set the working directory
WORKDIR /app

# Install required packages
RUN pip install --no-cache-dir flask kubernetes

# Copy the application code
COPY widget-controller.py .

# Set the command to run the application
CMD ["python", "widget-controller.py"]
