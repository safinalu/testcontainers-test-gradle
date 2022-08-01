import subprocess
import os

print(os.getcwd())
print(subprocess.run(["allure-combine", os.environ['PATH_TO_ALLURE_REPORT'], "--dest", os.environ['PATH_TO_RESULT'], "--auto-create-folders"]))