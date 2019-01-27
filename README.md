## Credit Card Demo Application
Simple Credit Card Management Application

![ScreenCast](screencast.gif)

### Prerequsites
1. The latest versions of **Java8**, **Maven** and **NodeJs** must be installed on your system. 
    
    Homebrew users use:
    ```bash
    brew cask install java8
    brew install maven
    brew install node
    ```
2. Install Angular CLI
```
npm install -g @angular/cli
```

### Run Server
```bash
cd server
mvn spring-boot:run
```

### Run Client
```bash
cd client
ng serve —-proxy-config proxy.conf.json --open
```