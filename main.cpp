#include <glad/glad.h>
#include <glfw3.h>
#include <iostream>
#include <vector>
#include <glm.hpp>
#include <gtc/constants.hpp>
#include <chrono>
#include "utils.hpp"

namespace IMN401 {


    void printProgramError(GLuint program)
    {
        GLuint isLinked;
        glGetProgramiv(program, GL_LINK_STATUS, (int*)&isLinked);
        if (isLinked == GL_FALSE)
        {
            GLint maxLength = 0;
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, &maxLength);
            // The maxLength includes the NULL character
            std::vector<GLchar> infoLog(maxLength);
            glGetProgramInfoLog(program, maxLength, &maxLength, &infoLog[0]);

            std::cout << "Error : " + std::string(infoLog.begin(), infoLog.end()) + "\n";

            // We don't need the program anymore.
            glDeleteProgram(program);
            return;
        }
        else
            std::cout << "Shader compilation : OK" << std::endl;


    }

    void printPipelineError(GLuint pipeline)
    {
        GLuint isValid;
        glGetProgramPipelineiv(pipeline, GL_VALIDATE_STATUS, (int*)&isValid);
        if (isValid == GL_FALSE)
        {
            GLint maxLength = 0;
            glGetProgramPipelineiv(pipeline, GL_INFO_LOG_LENGTH, &maxLength);
            // The maxLength includes the NULL character
            std::vector<GLchar> infoLog(maxLength);
            glGetProgramPipelineInfoLog(pipeline, maxLength, &maxLength, &infoLog[0]);

            std::cout << "Error : " + std::string(infoLog.begin(), infoLog.end()) + "\n";
            // We don't need the program anymore.
            glDeleteProgram(pipeline);
            return;
        }
        else
            std::cout << "Pipeline : OK" << std::endl;

    }






    int main()
    {
        // Init GLFW
        glfwInit();

        if (!glfwInit()) {
            std::cerr << "Failed to initialize GLFW" << std::endl;
            return EXIT_FAILURE;
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        const int width = 800;
        const int height = 800;

        GLFWwindow* window = glfwCreateWindow(width, height, "TP - From Scratch", NULL, NULL);
        glfwMakeContextCurrent(window);
        glfwSetWindowUserPointer(window, NULL);


        // Load all OpenGL functions using the glfw loader function
        if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
            std::cerr << "Failed to initialize OpenGL context" << std::endl;
            glfwTerminate();
            return EXIT_FAILURE;
        }


        // shaders
        std::string strVS = readFile("shaders/triangle-vs.glsl");
        const GLchar* vsCode = strVS.c_str();
        std::string strFS = readFile("shaders/triangle-fs.glsl");
        const GLchar* fsCode = strFS.c_str();



        // Initialization
        // =====================
        // TODO: init buffers, shaders, etc.
        // cf. https://www.khronos.org/files/opengl46-quick-reference-card.pdf


        const int n = 120;
        const float R = 0.5f;
        glm::vec3 vertices[n + 1];

        for (size_t i = 0; i < n; i++) {
            float Angle = (float)i * ((float)360 / (float)n);
            glm::vec3 point((float)R * (float)cos(Angle * 3.1416 / 180.0), (float)R * (float)sin(Angle * 3.1416 / 180.0), 1.0);
            vertices[i] = point;
        }
        glm::vec3 centre{ 0.0,0.0,0.0 };
        vertices[n] = centre;

        glm::uvec3 faces[n];

        for (size_t i = 0; i < n; i++) {
            faces[i] = { i,(i + 1) % n,n };
        }

            //create Buffers
       
        GLuint VBO;
        glCreateBuffers(1, &VBO);
        glNamedBufferData(VBO, sizeof(vertices), &vertices, GL_STATIC_DRAW);

        GLuint VBO1;
        glCreateBuffers(1, &VBO1);
        glNamedBufferData(VBO1, sizeof(faces), &faces, GL_STATIC_DRAW);

        //Initialisation de VA
        GLuint VAO;
        glCreateVertexArrays(1, &VAO);
        glEnableVertexArrayAttrib(VAO, 0);
        glVertexArrayAttribFormat(VAO, 0, 3, GL_FLOAT, GL_FALSE, 0);
        glVertexArrayVertexBuffer(VAO, 0, VBO, 0, sizeof(glm::vec3));
        glVertexArrayAttribBinding(VAO, 0, 0);
        glVertexArrayElementBuffer(VAO, VBO1);


        // Création des objets Program
        GLuint ShaderProgram = glCreateShaderProgramv(GL_VERTEX_SHADER, 1, &vsCode);
        GLuint fragmentProgram = glCreateShaderProgramv(GL_FRAGMENT_SHADER, 1, &fsCode);

        // Création de l'objet ProgramPipeline
        GLuint pipeline;
        glCreateProgramPipelines(1, &pipeline);

        // Spécifier les Programs à utiliser
        glUseProgramStages(pipeline, GL_VERTEX_SHADER_BIT, ShaderProgram);
        glUseProgramStages(pipeline, GL_FRAGMENT_SHADER_BIT, fragmentProgram);

        // Valider le pipeline avant l'exécution
        glValidateProgramPipeline(pipeline);

        // Vérification des erreurs de compilation
       printProgramError(ShaderProgram);
       printProgramError(fragmentProgram);
       printPipelineError(pipeline);


        if (glGetError() != GL_NO_ERROR) {
            std::cerr << "OpenGL error" << std::endl;
            return EXIT_FAILURE;
        }
        // ====================
        glClearColor(0.75, 0.5, 0.25, 1.0);

        GLuint Location = glGetUniformLocation(ShaderProgram, "timeSinceStart");
        auto start_time = std::chrono::high_resolution_clock::now();
     


        // Rendering loop
        while (!glfwWindowShouldClose(window))
        {
            // Handle events
            glfwPollEvents();

         
            //lClearColor(1.0f, 0.5f, 0.2f, 1.0f);
             // ==================
               // Effacement de l'écran
            glClear(GL_COLOR_BUFFER_BIT);
            glBindVertexArray(VAO);

            // Lier le pipeline avant le rendu
            glBindProgramPipeline(pipeline);

            auto current_time = std::chrono::high_resolution_clock::now();
            auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(current_time - start_time).count();
            glProgramUniform1f(ShaderProgram, Location, (GLfloat)elapsed_time);

            glDrawElements(GL_TRIANGLES, 3 * n, GL_UNSIGNED_INT, 0);


            // ==================

            glfwSwapBuffers(window);
        }

        // Clean up


        // Clean up
        
        glDeleteVertexArrays(1, &VAO);
        glDeleteBuffers(1, &VBO);
        glDeleteBuffers(1, &VBO1);
        glDeleteProgram(ShaderProgram);
        glDeleteProgram(fragmentProgram);
        glDeleteProgramPipelines(1, &pipeline);
        
        glfwTerminate();

        return EXIT_SUCCESS;
    }
}

int main()
{
    return IMN401::main();
}