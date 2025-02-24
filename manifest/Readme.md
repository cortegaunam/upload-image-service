# Ejecución de Manifiestos en Kubernetes con Tekton

Este documento describe los pasos para ejecutar los siguientes manifiestos en un clúster de Kubernetes utilizando Tekton:

- `gitclone.yaml`: Ejecuta una `TaskRun` basada en la tarea `git-clone` para clonar el repositorio del proyecto.
- `maven-taskrun-diplo.yaml`: Ejecuta una `TaskRun` basada en la tarea `maven` para construir el artefacto `app.jar`.
- `build-push.yaml`: Ejecuta una `TaskRun` basada en la tarea `buildah` para construir la imagen del microservicio y publicarla en DockerHub.

## Prerrequisitos

Antes de ejecutar los manifiestos, asegúrate de cumplir con los siguientes requisitos:

- Un clúster de Kubernetes configurado y en ejecución.
- Tekton Pipelines y Tekton CLI (`tkn`) instalados.
- Acceso a DockerHub con credenciales configuradas en el clúster.
- `kubectl` configurado para interactuar con el clúster.

## 1. Clonación del Repositorio

Ejecuta el siguiente comando para aplicar el manifiesto `gitclone.yaml`:

```sh
kubectl create -f gitclone.yaml
```

Verifica la ejecución con:

```sh
tkn taskrun list
```

Para ver los logs de ejecución:

```sh
tkn taskrun logs <nombre-del-taskrun> -f
```

## 2. Construcción del Artefacto con Maven

Ejecuta el siguiente comando para aplicar el manifiesto `maven-taskrun-diplo.yaml`:

```sh
kubectl create -f maven-taskrun-diplo.yaml
```

Verifica la ejecución con:

```sh
tkn taskrun list
```

Para ver los logs de ejecución:

```sh
tkn taskrun logs <nombre-del-taskrun> -f
```

## 3. Construcción y Publicación de la Imagen

Ejecuta el siguiente comando para aplicar el manifiesto `build-push.yaml`:

```sh
kubectl create -f build-push.yaml
```

Verifica la ejecución con:

```sh
tkn taskrun list
```

Para ver los logs de ejecución:

```sh
tkn taskrun logs <nombre-del-taskrun> -f
```

## 4. Verificación de la Imagen en DockerHub

Una vez finalizada la ejecución, es posible verificar que la imagen se publicó correctamente en DockerHub:

```sh
docker pull richyortega/upload-image-service:latest
```

Si necesitas eliminar los recursos creados:

```sh
kubectl delete -f gitclone.yaml -f maven-taskrun-diplo.yaml -f build-push.yaml
```

## Conclusión

Siguiendo estos pasos, se logra clonar el repositorio, construir el artefacto con Maven, generar la imagen del microservicio y publicarla en DockerHub utilizando Tekton en Kubernetes.

