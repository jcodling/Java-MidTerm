/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* Jeff Codling - c0471944 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 * Provides an Account Balance and Basic Withdrawal/Deposit Operations
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    
    public Account account = new Account();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            out.println(account.getBalance());
            
        } catch (IOException ex) {
            response.setStatus(500);
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if(!request.getParameterNames().hasMoreElements()) {
            // No parameters
            response.setStatus(500);
        } else {
            // Parameters so check which ones
            try(PrintWriter out = response.getWriter()) {
                response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                
                if(request.getParameter("debit") != null) {
                    account.debit(Double.parseDouble(request.getParameter("debit")));
                } else if (request.getParameter("credit") != null) {
                    account.credit(Double.parseDouble(request.getParameter("credit")));
                } else if (request.getParameter("close") != null) {
                    if(request.getParameter("close").equals("true")) {
                        account.close();
                    }
                }
                out.println(account.getBalance());
            } catch (IOException ex) {
                response.setStatus(500);
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
