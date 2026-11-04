#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p build
find src test -name '*.java' > build/fontes.txt
javac --release 20 -encoding UTF-8 -Xlint:all -d build @build/fontes.txt
case "${1:-}" in
  --testar) java -cp build restaurante.Testes ;;
  --demo) java -cp build restaurante.app.Main --demo ;;
  *) java -cp build restaurante.app.Main ;;
esac
