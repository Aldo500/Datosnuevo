package com.example.splash.Json;


import android.util.Base64;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.getInstance;

public class MyDesUtil implements Serializable
{
    public static String PROVIDER = "DESede/ECB/PKCS7Padding";
    public static String ALGORITHM =  "DESede";
    private String stringKeyBase64;
    private Cipher cipherCifrar;
    private Cipher cipherDesCifrar;
    private SecretKey secretKey;

    public MyDesUtil()
    {
    }

    public MyDesUtil(String stringKeyBase64)
    {
        this.stringKeyBase64 = stringKeyBase64;
    }

    public MyDesUtil addStringKeyBase64( String stringKeyBase64 )
    {
        this.stringKeyBase64 = stringKeyBase64;
        return this;
    }

    public boolean loadCipher( MODO modo )
    {
        Cipher cipher = null;
        try
        {
            cipher = Cipher.getInstance(PROVIDER);
            if( cipher == null )
            {
                return false;
            }
            if( secretKey == null )
            {
                if( stringKeyBase64 != null && stringKeyBase64.length( ) > 0 )
                {
                    if( !loadSecreteKeyByStringKey( ) )
                    {
                        return false;
                    }
                }
                else
                {
                    if(!loadSecreteKey() )
                    {
                        return false;
                    }
                }
            }
            cipher.init(modo.getModo(), secretKey );
            if(MODO.CIFRAR.equals( modo ))
            {
                cipherCifrar = cipher;
            }
            else
            {
                cipherDesCifrar = cipher;
            }
            return cipher != null;
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String cifrar( String textoEnClaro )
    {
        byte[] bytes = null;
        byte[] bytesCifrados = null;
        byte[] bytesBase64 = null;

        try
        {
            if( textoEnClaro == null || textoEnClaro.length() == 0 )
            {
                return null;
            }
            if( cipherCifrar == null )
            {
                if( !loadCipher(MODO.CIFRAR) )
                {
                    return null;
                }
            }
            bytes = textoEnClaro.getBytes(StandardCharsets.UTF_8);
            bytesCifrados = cipherCifrar.doFinal( bytes );
            bytesBase64 = Base64.encode(bytesCifrados, Base64.DEFAULT);
            return new String(bytesBase64);
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String desCifrar(String textoCifradoBase64 )
    {
        byte[] bytes = null;
        byte[] bytesBase64 = null;

        try
        {
            if( textoCifradoBase64 == null || textoCifradoBase64.length() == 0 )
            {
                return null;
            }
            if( cipherDesCifrar == null )
            {
                if( !loadCipher( MODO.DESCIFRAR ) )
                {
                    return null;
                }
            }
            bytesBase64 = Base64.decode( textoCifradoBase64.getBytes() , Base64.DEFAULT );
            bytes = cipherDesCifrar.doFinal( bytesBase64 );
            return new String( bytes , StandardCharsets.UTF_8 );
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean loadSecreteKey(  )
    {
        try
        {
            secretKey = KeyGenerator.getInstance( ALGORITHM ).generateKey( );
            return secretKey != null;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadSecreteKeyByStringKey( )
    {
        byte[] bytes = null;
        bytes = Base64.decode( stringKeyBase64 , Base64.DEFAULT);
        secretKey = new SecretKeySpec( bytes , 0, bytes.length , ALGORITHM );
        return secretKey != null;
    }

    public String toStringSecreteKey( )
    {
        if( secretKey == null )
        {
            return null;
        }
        return Base64.encodeToString( secretKey.getEncoded( ) , Base64.DEFAULT);
    }

    public enum MODO
    {
        CIFRAR( Cipher.ENCRYPT_MODE ),
        DESCIFRAR(Cipher.DECRYPT_MODE);
        private int modo;
        MODO(int modo)
        {
            this.modo = modo;
        }

        public int getModo()
        {
            return modo;
        }
    }
}

