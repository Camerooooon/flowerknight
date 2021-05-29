package org.dhsdev.flowerknight.gl;

import static org.lwjgl.opengl.GL33.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * A texture we can use.
 * @author adamhutchings
 */
public class Texture {

    /**
     * The OpenGL ID of the texture.
     */
    private final int texId;

    /**
     * Create a texture from a file.
     * @param fileName the name of the file
     * @throws Exception if something goes wrong
     */
    public Texture(String fileName) throws IOException {

        PNGDecoder decoder = new PNGDecoder(new FileInputStream(fileName));

        int width = decoder.getWidth(), height = decoder.getHeight();

        ByteBuffer buf = ByteBuffer.allocateDirect(
                4 * width * height
        );
        decoder.decode(buf, width * 4, Format.RGBA);
        buf.flip();

        texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width,
                height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf
        );

        glGenerateMipmap(GL_TEXTURE_2D);

    }

    /**
     * The ID of this texture, publicly visible.
     * @return
     */
    public int id() {
        return texId;
    }

    /**
     * Delete this texture
     */
    public void delete() {
        glDeleteTextures(texId);
    }

}