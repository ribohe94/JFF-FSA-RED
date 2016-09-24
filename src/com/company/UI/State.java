package com.company.UI;

/**
 * Created by ribohe94 on 23/09/16.
 *
 * Interfaz de estados. Es utilizada únicamente para la interfaz de usuario.
 *
 */
public interface State {
    public State next(Input in);
}
