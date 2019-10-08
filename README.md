## Приложение для подсчета текущей стоимости портфеля акций и их распределение по секторам.

1. Перед началом работы необходимо положить свой токен IEX Cloud в файл application.properties с ключом iex.cloud.token.

2. Сборка приложения:
    mvn clean install

3. Запуск приложения:
    mvn clean spring-boot:run


4. Локальный эндпоинт http://localhost:8080/stock-portfolio-allocation/v1/stock-portfolio/calculate

#### Пример запроса:
     {
        "stocks":[
          {
             "symbol":"AAPL",
             "volume":50
          },
          {
             "symbol":"HOG",
             "volume":100
          },
          {
             "symbol":"MDSO",
             "volume":1
          },
          {
             "symbol":"IDRA",
             "volume":1
          },
          {
             "symbol":"MRSN",
             "volume":1
          }
        ]
    }

#### Пример ответа:
    {
     "value":1600000,
     "allocations":[
      {
         "sector":"Technology",
         "assetValue":600000,
         "proportion":0.375
      },
      {
         "sector":"Healthcare",
         "assetValue":1000000,
         "proportion":0.625
      }
     ]
    }


