for file in *-datasource.json ; do
  if [ -e "$file" ] ; then
    echo "importing $file" &&
    curl --silent --fail --show-error \
      --request POST http://${GF_ADMIN_USER}:${GF_ADMIN_PASSWORD}@grafana:3000/api/datasources \
      --header "Content-Type: application/json" \
      --data-binary "@$file" ;
    echo "" ;
  fi
done ;
for file in *-dashboard.json ; do
  if [ -e "$file" ] ; then
    echo "importing $file" &&
    ( echo '{"dashboard":'; \
      cat "$file"; \
      echo ',"overwrite":true,"inputs":[{"name":"DS_PROMETHEUS","type":"datasource","pluginId":"prometheus","value":"prometheus"}]}' ) \
    | jq -c '.' \
    | curl --silent --fail --show-error \
      --request POST http://${GF_ADMIN_USER}:${GF_ADMIN_PASSWORD}@grafana:3000/api/dashboards/import \
      --header "Content-Type: application/json" \
      --data-binary "@-" ;
    echo "" ;
  fi
done