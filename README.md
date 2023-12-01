# Lending backend challenge

## Running the app

Run this using [sbt](http://www.scala-sbt.org/).

```bash
sbt run
```

And then go <http://localhost:9000/loan/report?reportType=Amount&reportFilter=Grade> to see one of the available data views.

## Route and parameters

- `/loan/report`:

  * reportType: Selector between dollar amount vs total count reports, valid values:
    * Amount
    * Count
  * reportFilter: Selector between the available grouping dimension to display the data, valid values:
    * Grade
    * State
    * Purpose
    * JobTitle

## Sample response
```JSON
{
  "title": "Total Loans by Grade",
  "xLabel": "Grades",
  "yLabel": "Total Loans - Millions",
  "data": [
    {
      "label": "B",
      "value": "555179425"
    },
    {
      "label": "C",
      "value": "504409350"
    },
    {
      "label": "A",
      "value": "364672175"
    },
    {
      "label": "D",
      "value": "278252525"
    },
    {
      "label": "E",
      "value": "79555175"
    },
    {
      "label": "F",
      "value": "24585600"
    },
    {
      "label": "G",
      "value": "10512425"
    }
  ]
}
```
