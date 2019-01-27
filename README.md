## Credit Card Demo Application
Simple Credit Card Management Application

<video src="screencast.mp4" controls>
  Your browser cannot show this video. Please view it on <a href="https://www.useloom.com/share/3d37aae7f11e4fb8959c3ffac630fb58">loom</a>.
</video>

### Prerequsites
1. The latest versions of **Java8**, **Maven** and **NodeJs** must be installed on your system. 
    
    For Homebrew users use:
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