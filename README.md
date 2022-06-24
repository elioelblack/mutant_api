
# API MUTANTES

El algoritmo detecta si un humano es 
mutante basándose en su secuencia de ADN

Las letras de los Strings solo pueden ser: (A,T,C,G).
Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras 
iguales, de forma oblicua, horizontal o vertical. 
## API Reference

#### Analizar cadena de DNA

```http
  POST /mutant
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `dna` | `Array string` | **Required**.JSON con campo dna que contiene aun array string |

#### Get item

```http
  GET /stats
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `ninguno`      | | Devuelve estadisticas: un Json con las estadísticas de las 
verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}  |




## Demo

http://74.208.187.67:8080/mutant-api


## Installation

Importar proyecto en su IDE de preferencia

```bash
  Run with Java o Maven
```
    
## Optimizations

Utiliza Cache para el servicio de analisis de DNA para rendimiento, además guarda todos los análisis una sola vez, si ya existe simplemente devuelve si es mutante o no.
## Tech Stack


**Java:** Spring boot, h2 database, validation, codec apache, data-jpa, lombok, Maven


## Licencia

[![MIT License](https://img.shields.io/apm/l/atomic-design-ui.svg?)](https://github.com/elioelblack)


![Logo](https://www.lavanguardia.com/uploads/2021/02/02/6019110f610d0.jpeg)

