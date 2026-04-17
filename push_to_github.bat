@echo off
echo Configuring git...
git config --global user.name "audace011"
git config --global user.email "karenaudace@gmail.com"

echo Initializing repository...
git init

echo Adding files...
git add .

echo Committing...
git commit -m "Initial commit of Spring Boot backend"

echo Setting branch to main...
git branch -M main

echo Adding remote...
git remote add origin https://github.com/Audace011/taskbuddy-api.git

echo Pushing to GitHub...
git push -u origin main

echo.
echo =========================================
echo Done! Your backend code is now on GitHub.
echo =========================================
pause
