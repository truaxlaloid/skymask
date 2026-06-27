#version 150
uniform sampler2D DiffuseSampler;
uniform float Threshold;
uniform float MaskOnly;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
   
    if (brightness >= Threshold) {
        fragColor = vec4(1.0, 0.0, 0.0, 1.0);
    } else {
        if (MaskOnly > 0.5) {
            fragColor = vec4(0.0, 0.0, 0.0, 1.0);
        } else {
            fragColor = color;
        }
    }
}
