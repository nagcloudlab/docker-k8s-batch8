from flask import Flask
from threading import Thread
from kubernetes import client, config, watch
import time

# Health check endpoint
app = Flask(__name__)

@app.route('/healthz')
def health():
    return "ok", 200

def run_health_server():
    app.run(host="0.0.0.0", port=8080)

# Main controller logic
def run_controller():
    try:
        config.load_incluster_config()
        api = client.CustomObjectsApi()

        print("🚀 Widget Controller is running...")

        w = watch.Watch()
        for event in w.stream(api.list_namespaced_custom_object, "example.com", "v1", "default", "widgets"):
            widget = event["object"]
            event_type = event["type"]

            name = widget["metadata"]["name"]
            color = widget["spec"]["color"]
            size = widget["spec"]["size"]
            # // based on current state of the widget, we can take actions
            print(f"🛠️ {event_type}: Widget '{name}' - Color: '{color}', Size: '{size}'")

    except Exception as e:
        print(f"⚠️ Error: {e}")

if __name__ == "__main__":
    # Run health server in background
    Thread(target=run_health_server).start()
    run_controller()
