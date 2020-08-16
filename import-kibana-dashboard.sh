dashboard_json=$(cat ./config/dashboard.json)
curl -X POST "localhost:5601/api/kibana/dashboards/import" -H 'kbn-xsrf: true' -H 'Content-Type: application/json' -d"$dashboard_json"
