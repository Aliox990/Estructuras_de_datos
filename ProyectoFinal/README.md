# PlayLIst de Musica
-----------

## Descripción

- Aplicación de escritorio en Java que gestiona una playlist de reproducción, cola y
historial. Usa estructuras de datos propias (lista doblemente enlazada, cola y pila) y
persistencia simple en archivos de texto dentro de la carpeta `db/`.
--------------

## Cómo compilar
Desde la raíz del proyecto:
```bash
javac -encoding UTF-8 -d bin src/*.java
```
--------------

## Cómo ejecutar
Usa el script de lanzamiento:
```bash
bash launch.sh
```
---------------

## Generar Javadoc
La documentación Javadoc ya está generada en la carpeta `doc/`. Para regenerarla manualmente:
```bash
cd ProyectoFinal
javadoc -d doc -author -version -encoding UTF-8 -charset UTF-8 src/*.java
```
---------------

## Estructura de archivos relevantes
- `src/` : código fuente Java
- `bin/` : clases compiladas (generadas por `javac` o por `launch.sh`)
- `dist/` : archivo ejecutable JAR (generado por `launch.sh`)
- `db/`  : archivos de datos persistentes (`BaseDatos.txt`, `PlaylistCanciones.txt`)
- `doc/` : documentación Javadoc generada
---------------

## Notas
- El proyecto crea por defecto la carpeta `db/` y inicializa `BaseDatos.txt` con usuarios
	por defecto (`admin;1234;administrador`, `usuario1;0000;usuario`) en el primer arranque.
---------------

## Datos del proyecto
- **Autores**: Christian Alonso Arevalos Gonzalez y Cesar de Jesus Becerra Vera
- **Versión**: 1.0
- **Centro**: Centro Universitario de los Altos / Universidad de Guadalajara
- **Profesor**: Maria Obdulia Gonzalez Fernandez
