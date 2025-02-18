uniform float timeSinceStart;

out gl_PerVertex {
    vec4 gl_Position;
};

layout (location = 0) in vec3 position;
out vec2 FragPos;

void main()
{
    // Calcul de la vitesse et de l'amplitude en fonction du temps
    float speed = timeSinceStart / 300.0;
    float amplitude = 0.8 + 0.2 * sin(timeSinceStart / 1000.0); // Variation du rayon

    // Calcul de la position centrale du cercle en fonction du temps
    float centerX = sin(timeSinceStart / 1000.0) * 0.5;
    float centerY = cos(timeSinceStart / 1000.0) * 0.5;

    // Position animée du cercle sur toute la fenêtre
    float x = centerX + position.x * amplitude * cos(speed);
    float y = centerY + position.y * amplitude * sin(speed);

    // Mise à jour de la position du vertex
    gl_Position = vec4(x, y, position.z, 1.0);
    FragPos = gl_Position.xy;
}