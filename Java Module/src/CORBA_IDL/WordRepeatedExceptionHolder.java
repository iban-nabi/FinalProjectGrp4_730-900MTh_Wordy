package CORBA_IDL;

/**
* CORBA_IDL/WordRepeatedExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from GameActions.idl
* Tuesday, May 9, 2023 9:47:20 AM CST
*/

public final class WordRepeatedExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public CORBA_IDL.WordRepeatedException value = null;

  public WordRepeatedExceptionHolder ()
  {
  }

  public WordRepeatedExceptionHolder (CORBA_IDL.WordRepeatedException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CORBA_IDL.WordRepeatedExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CORBA_IDL.WordRepeatedExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CORBA_IDL.WordRepeatedExceptionHelper.type ();
  }

}