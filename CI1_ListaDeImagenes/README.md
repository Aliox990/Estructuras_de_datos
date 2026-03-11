# CI1 - Lista de Imágenes

Aplicación GUI en Java para gestionar una galería de imágenes utilizando una **lista doblemente enlazada**.

## 👥 Autores
- **Cesar de Jesus Becerra Vera**
- **Christian Alonso Arevalos Gonzalez**

**Universidad de Guadalajara - Centro Universitario de los Altos**  
**Materia:** Estructuras de Datos  
**Fecha:** Marzo 2026

---

## 📋 Descripción

Sistema de gestión de imágenes que implementa una estructura de datos de lista doblemente enlazada. Permite agregar, visualizar, navegar y eliminar imágenes con sus respectivos metadatos (nombre, descripción y fecha de registro).

### Características principales:
- ✅ Agregar imágenes (JPG, PNG, JPEG) con nombre y descripción
- ✅ Navegación completa: Primera, Última, Anterior, Siguiente
- ✅ Eliminación de imágenes con confirmación
- ✅ Visualización automática con escalado proporcional
- ✅ Registro de fecha y hora de cada imagen

---

## 🛠️ Requisitos

- **Java JDK 8 o superior**
- Sistema operativo: Linux, Windows o macOS

---

## 🚀 Compilación

### Desde la terminal:
```bash
cd CI1_ListaDeImagenes/src
javac Lista_de_imagenes.java Nodo.java
```

### Con NetBeans:
1. Abrir el proyecto en NetBeans
2. Presionar **F11** o clic en "Clean and Build"

---

## ▶️ Ejecución

### Desde la terminal:
```bash
cd CI1_ListaDeImagenes/src
java Lista_de_imagenes
```

### Con NetBeans:
- Presionar **F6** o clic en "Run Project"

---

## 📁 Estructura del Proyecto

```
CI1_ListaDeImagenes/
├── src/
│   ├── Lista_de_imagenes.java    # Clase principal con GUI
│   ├── Lista_de_imagenes.form    # Diseño de la interfaz
│   └── Nodo.java                 # Clase Nodo para lista doblemente enlazada
├── doc/                           # Documentación Javadoc (generada)
├── nbproject/                     # Configuración de NetBeans
└── README.md                      # Este archivo
```

---

## 📖 Documentación Javadoc

La documentación completa del código está disponible en formato Javadoc en la carpeta `doc/`.

### Generar documentación:
```bash
cd CI1_ListaDeImagenes
javadoc -d doc -encoding UTF-8 -author -version src/*.java
```

### Visualizar documentación:
Abrir el archivo `doc/index.html` en un navegador web.

---

## 🎮 Uso de la Aplicación

1. **Agregar imagen:**
   - Ingresar nombre y descripción
   - Clic en "Agregar"
   - Seleccionar archivo de imagen

2. **Navegar:**
   - `<<` Primera imagen
   - `<` Imagen anterior
   - `>` Imagen siguiente
   - `>>` Última imagen

3. **Eliminar:**
   - Navegar a la imagen deseada
   - Clic en el botón `X`
   - Confirmar eliminación

---

## 📝 Notas Técnicas

- **Estructura de datos:** Lista doblemente enlazada circular
- **Formato de fecha:** dd/MM/yyyy HH:mm:ss
- **Tipos de imagen soportados:** JPG, JPEG, PNG
- **Escalado:** Proporcional sin deformación

---

## 📄 Licencia

Proyecto académico - UDG Cualtos 2026
