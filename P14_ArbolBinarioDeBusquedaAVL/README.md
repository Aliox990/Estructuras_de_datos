# Árbol Binario de Búsqueda AVL (Auto-Balanceado)
**Materia:** Estructuras de Datos  
**UDG** Centro Universitario de los Altos  
**Equipo:** 
- Christian Alonso Arevalos Gonzalez
- Cesar de Jesus Becerra Vera
---

## Descripción
Este proyecto implementa un **Árbol Binario de Búsqueda Auto-Balanceado (AVL)** en Java. Un árbol AVL es una estructura de datos que mantiene sus nodos organizados de forma balanceada, garantizando que las operaciones fundamentales (inserción, eliminación y búsqueda) se realicen en tiempo O(log n).

### ¿Qué es un Árbol AVL?
Un Árbol AVL es un tipo especial de Árbol Binario de Búsqueda que se auto-balancea automáticamente mediante rotaciones. El balanceo se logra mediante el **Factor de Equilibrio (FE)**, que es la diferencia entre las alturas del subárbol derecho e izquierdo de cada nodo. Un árbol AVL se mantiene balanceado cuando el FE de cada nodo está entre -1 y 1.

### Operaciones Principales
1. **Inserción:** Agrega un nuevo valor al árbol manteniendo la propiedad de búsqueda y realizando rotaciones si es necesario para mantener el balance.
2. **Eliminación:** Elimina un nodo del árbol y rebalancea la estructura si es necesario.
3. **Búsqueda:** Busca un valor específico en el árbol.
4. **Recorridos:** 
   - **Preorden:** Raíz, Izquierda, Derecha
   - **Inorden:** Izquierda, Raíz, Derecha (produce valores ordenados)
   - **Postorden:** Izquierda, Derecha, Raíz
5. **Visualización:** Muestra el árbol de forma jerárquica en la consola.
---

## Estructura del Proyecto
```
P14_ArbolBinarioDeBusquedaAVL/
├── src/                     # Código fuente del proyecto
├── doc/                      # Documentación JavaDoc generada
│   └── p14_arbolbinariodebusquedaavl/
│       ├── Nodo.java              # Clase que representa un nodo del árbol
│       └── P14_ArbolBinarioDeBusquedaAVL.java  # Clase principal con operaciones AVL
├── build.xml                       # Script de construcción
├── manifest.mf                     # Manifiesto de la aplicación
└── README.md                       # Este archivo
```
---

## Compilación
### Usando NetBeans
1. Abrir el proyecto en NetBeans
2. Click derecho sobre el proyecto → **Build**
### Usando Apache Ant
```bash
ant clean build
```
---

## Generación de JavaDoc
Para generar la documentación JavaDoc:
```bash
ant javadoc
```
La documentación se generará en la carpeta `doc/` del proyecto.
### Visualizar la Documentación
Abre el archivo `doc/index.html` en tu navegador web para ver la documentación completa de las clases y métodos.
---

## Uso del Programa
Al ejecutar la aplicación, se presenta un menú interactivo con las siguientes opciones:
1. **Agregar elemento** - Inserta un nuevo valor al árbol
2. **Eliminar elemento** - Elimina un valor del árbol
3. **Buscar elemento** - Busca un valor específico
4. **Mostrar árbol** - Visualiza la estructura del árbol
5. **Recorridos:**
   - Preorden
   - Inorden
   - Postorden
6. **Salir** - Cierra la aplicación
### Ejemplo de Uso
```
Insertar: 10, 5, 15, 2, 7, 12, 20
Mostrar árbol (visualiza la estructura balanceada)
Recorrido inorden (muestra valores ordenados)
Buscar: 7 (encuentra el valor en el árbol)
Eliminar: 5 (remueve el nodo y rebalancea)
```
---

## Requisitos
- **Java 8+**
- **NetBeans IDE** (recomendado) o compilador Java en línea de comandos
- **Apache Ant** (para compilación desde terminal)
---
