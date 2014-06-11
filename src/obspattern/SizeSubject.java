/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package obspattern;

import java.util.ArrayList;

/**
 *
 * @author RO100051
 */
public interface SizeSubject {
    ArrayList<SizeListener> listener = new ArrayList<>();

    public void addListener(SizeListener sl);
    public void notificaListener(long size);
}
