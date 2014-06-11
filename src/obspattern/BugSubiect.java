/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package obspattern;

import beans.Bugs;
import java.util.ArrayList;

/**
 *
 * @author RO100051
 */
public interface BugSubiect {
    ArrayList<BugListener> listeners = new ArrayList<>();
    public void addBugListener(BugListener list);
    public void notificaListener(Bugs bug);
}
