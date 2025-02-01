# Diplomado Desarrollo y despliegue de aplicaciones Cloud Native en ambientes híbridos
## Upload Image Service

Microservicio para la carga de imagenes que serán convertidas a formato tiff piramidal

## Estrategia de Ramas basada en Características

Este repositorio sigue una estrategia de control de versiones basada en ramas por características (Feature Branching), asegurando un flujo de desarrollo estructurado y colaborativo.

### Flujo de Trabajo

1. **Rama Principal (`main`)**: Contiene el código en producción. Solo se actualiza con versiones estables y probadas.
2. **Rama de Desarrollo (`develop`)**: Rama base para la integración de nuevas características. Aquí se validan los cambios antes de pasar a `main`.
3. **Ramas de Características (`feature/NombreCaracteristica`)**: Cada nueva funcionalidad o mejora se desarrolla en una rama independiente derivada de `develop`.

### Pasos para Añadir una Nueva Característica

1. Crear una nueva rama basada en `develop`:
   ```sh
   git checkout develop
   git pull origin develop
   git checkout -b feature/nueva-caracteristica
   ```
2. Implementar la funcionalidad en la rama `feature/nueva-caracteristica`.
3. Hacer *commits* con cambios incrementales y descriptivos:
   ```sh
   git add .
   git commit -m "Añade nueva funcionalidad X"
   ```
4. Subir la rama al repositorio remoto:
   ```sh
   git push origin feature/nueva-caracteristica
   ```
5. Crear un **Pull Request (PR)** para fusionar `feature/nueva-caracteristica` en `develop`.
6. Revisar el código, realizar pruebas y aprobar el PR.
7. Fusionar el PR en `develop` y eliminar la rama de característica:
   ```sh
   git checkout develop
   git pull origin develop
   git branch -d feature/nueva-caracteristica
   git push origin --delete feature/nueva-caracteristica
   ```
8. Una vez validadas las nuevas características en `develop`, generar un PR para fusionar `develop` en `main`.
9. Etiquetar la versión estable en `main`:
   ```sh
   git checkout main
   git pull origin main
   git merge develop
   git tag -a vX.Y.Z -m "Versión estable X.Y.Z"
   git push origin main --tags
   ```

### Consideraciones
- Siempre actualizar `develop` antes de crear una rama de característica.
- Seguir una convención de nombres clara para las ramas (`feature/nombre-descriptivo`).
- Mantener ramas de características pequeñas y específicas para facilitar revisiones.
- Realizar revisiones de código antes de integrar cambios en `develop`.
- Asegurar pruebas y validaciones antes de fusionar `develop` en `main`.

Este flujo ayuda a mantener un desarrollo organizado, colaborativo y con código de alta calidad listo para producción.
