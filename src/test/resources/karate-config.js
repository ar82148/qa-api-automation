function fn() {
    var apiKey = java.lang.System.getenv('REQRES_PUBLIC_KEY') ||
                 karate.properties['REQRES_PUBLIC_KEY'];
    karate.configure('headers', { 'x-api-key': apiKey });
    var config = {
        baseUrl: 'https://reqres.in/api'
    };
    return config;
}