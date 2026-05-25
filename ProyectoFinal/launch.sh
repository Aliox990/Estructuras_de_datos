# ================================================================
# Script de lanzamiento para producción - PlayLIst de Musica
# @author Christian Alonso Arevalos Gonzalez y Cesar de Jesus Becerra Vera
# @version 1.0
# ARCHIVO: launch.sh
# CENTRO UNIVERSITARIO DE LOS ALTOS / UNIVERSIDAD DE GUADALAJARA
# INGENIERIA EN COMPUTACION / 4TO SEMESTRE
# PROFESOR: MARIA OBDULIA GONZALEZ FERNANDEZ
# DESCRIPCIÓN: Script para compilar, documentar, empaquetar y ejecutar el proyecto de reproducción de música.
# Compatible: Linux, macOS, Windows (Git Bash/WSL)
# ================================================================

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

DIST_DIR="dist"
BIN_DIR="bin"

echo "========================================="
echo "  PlayLIst de Musica - Despliegue a Producción"
echo "========================================="
echo ""

mkdir -p "$DIST_DIR" "$BIN_DIR" db

echo "🧹 Limpiando compilaciones anteriores..."
rm -rf "$BIN_DIR"/*
rm -rf "$DIST_DIR"/*

echo "🔨 Compilando el proyecto..."
javac -encoding UTF-8 -d "$BIN_DIR" src/*.java

if [ $? -ne 0 ]; then
	echo "❌ Error en la compilación."
	exit 1
fi

echo "✅ Compilación exitosa."

echo " Creando archivo JAR..."
cd "$BIN_DIR"

jar cfe "../$DIST_DIR/playlist_musica.jar" ReproductorDeMusica -C . .
cd ..

if [ $? -eq 0 ]; then
	echo "✅ JAR creado exitosamente en $DIST_DIR/playlist_musica.jar"
	echo ""
	echo "📝 Iniciando la aplicación..."
	echo ""
	java -jar "$DIST_DIR/playlist_musica.jar"
else
	echo "❌ Error al crear el JAR."
	exit 1
fi
