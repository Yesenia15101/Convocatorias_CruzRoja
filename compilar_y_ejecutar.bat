@echo off
if not exist out mkdir out
javac --add-modules jdk.httpserver -encoding UTF-8 -d out src\Main.java src\bc3_inscripciones\dominio\enums\EstadoInscripcion.java src\bc3_inscripciones\dominio\entities\Inscripcion.java src\bc3_inscripciones\dominio\repositories\IInscripcionRepositorio.java src\bc3_inscripciones\aplicacion\dto\InscripcionDTO.java src\bc3_inscripciones\aplicacion\interfaces\IInscripcionServicio.java src\bc3_inscripciones\aplicacion\services\InscripcionServicioAplicacion.java src\bc3_inscripciones\infraestructura\persistence\InscripcionRepositorioImpl.java src\bc3_inscripciones\presentacion\controllers\InscripcionController.java
if errorlevel 1 pause & exit /b 1
java --add-modules jdk.httpserver -cp out Main
pause
