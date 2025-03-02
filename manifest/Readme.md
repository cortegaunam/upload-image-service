# Ejecución de Manifiestos en Kubernetes con Tekton

Este documento describe los pasos para ejecutar los siguientes manifiestos en un clúster de Kubernetes utilizando Tekton.

## Estructura de Archivos

Dentro del repositorio, los manifiestos se encuentran organizados de la siguiente manera:

### Carpeta `taskruns`

- `gitclone.yaml`: Ejecuta una `TaskRun` basada en la tarea `git-clone` para clonar el repositorio del proyecto.
- `maven-taskrun-diplo.yaml`: Ejecuta una `TaskRun` basada en la tarea `maven` para construir el artefacto `app.jar`.
- `build-push.yaml`: Ejecuta una `TaskRun` basada en la tarea `buildah` para construir la imagen del microservicio y publicarla en DockerHub.

### Carpeta `tekton/pipelines`

- `pipeline-git-clone-package.yaml`: Define una `Pipeline` que realiza solo la clonación del código y la generación y publicación de la imagen de contenedor del microservicio.
- `pipelinerun-git-clone-push.yaml`: Ejecuta la `Pipeline` definida en `pipeline-git-clone-package.yaml`.
- `pipeline-cicd.yaml`: Define una `Pipeline` que ejecuta el flujo completo de CI/CD, desde la clonación del código, la generación y publicación de la imagen de contenedor del microservicio y su despliegue en kubernetes.
- `pipelinerun-cicd.yaml`: Ejecuta la `Pipeline` definida en `pipeline-cicd.yaml`.

## Prerrequisitos

Antes de ejecutar los manifiestos, asegúrate de cumplir con los siguientes requisitos:

- Un clúster de Kubernetes configurado y en ejecución.
- Tekton Pipelines y Tekton CLI (`tkn`) instalados.
- Acceso a DockerHub con credenciales configuradas en el clúster.
- `kubectl` configurado para interactuar con el clúster.

## 1. Ejecución de `TaskRun`

### Clonación del Repositorio

```sh
kubectl create -f taskruns/gitclone.yaml
```

### Construcción del Artefacto con Maven

```sh
kubectl create -f taskruns/maven-taskrun-diplo.yaml
```

### Construcción y Publicación de la Imagen

```sh
kubectl create -f taskruns/build-push.yaml
```

### Verificación de la Ejecución

```sh
tkn taskrun list
tkn taskrun logs <nombre-del-taskrun> -f
```

## 2. Ejecución de `PipelineRun`

### Ejecución de la Pipeline de Clonación y Empaquetado

```sh
kubectl create -f tekton/pipelines/pipelinerun-git-clone-push.yaml
```

### Ejecución de la Pipeline CI/CD Completa

```sh
kubectl create -f tekton/pipelines/pipelinerun-cicd.yaml
```

Dado que los archivos `pipelinerun-git-clone-push.yaml` y `pipelinerun-cicd.yaml` usan `generateName`, es necesario listar los `PipelineRun` creados para ver su nombre:

```sh
tkn pipelinerun list
```

Para ver los logs de ejecución:

```sh
tkn pipelinerun logs <nombre-del-pipelinerun> -f
```

## 3. Verificación de la Imagen en DockerHub

Una vez finalizada la ejecución, es posible verificar que la imagen se publicó correctamente en DockerHub:

```sh
docker pull richyortega/proyecto-diplomado:<TAG>
```

Si necesitas eliminar los recursos creados:

```sh
kubectl delete -f taskruns/gitclone.yaml -f taskruns/maven-taskrun-diplo.yaml -f taskruns/build-push.yaml -f tekton/pipelines/pipelinerun-cicd.yaml -f tekton/pipelines/pipelinerun-git-clone-push.yaml
```

## 4. Verificación del Deployment

Una vez ejecutado el pipelinerun `pipelinerun-cicd.yaml` tambien es posible verificar el deployment:

```sh
kubectl get deployments
```

## Conclusión

Siguiendo estos pasos, se logra clonar el repositorio, construir el artefacto con Maven, generar la imagen del microservicio, publicarla en DockerHub y realizar su depliegue en kubernetes, todo mediante Tekton.
