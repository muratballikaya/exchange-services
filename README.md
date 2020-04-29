# exchange-api

This exchange api uses "https://ratesapi.io" to get the rate values.

You can run online on Heroku.
Api documantation :
https://exchangeservice.herokuapp.com/swagger-ui.html

To calculate converting some currency to an other, please use .../exchange/convert api.
https://exchangeservice.herokuapp.com/exchange/convert?sourceCurrency=USD&sourceAmount=1000&targetCurrency=EUR

To list the calculations, please use .../exchange/listconversion api.
* page and size parameters are optonal. You can check api docs.
* at least one of id or date must be typed.
https://exchangeservice.herokuapp.com/exchange/listconversion?id=1&date=29-04-2020&page=0&size=10

