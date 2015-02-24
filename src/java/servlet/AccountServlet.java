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
    
    private Account account = new Account();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try(PrintWriter out = response.getWriter()) {
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            out.println(account.getBalance());
            
        } catch (IOException ex) {
            response.setStatus(500);
//            Logger.log(Level.SEVERE, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if(!request.getParameterNames().hasMoreElements()) {
            // No parameters
            response.setStatus(500);
        } else {
            try(PrintWriter out = response.getWriter()) {
                response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                if(!request.getParameter("debit").isEmpty()) {
                    account.debit(Double.parseDouble(request.getParameter("debit")));
                } else if (!request.getParameter("credit").isEmpty()) {
                    account.credit(Double.parseDouble(request.getParameter("credit")));
                } else if (!request.getParameter("close").isEmpty()) {
                    if(request.getParameter("close").equals("TRUE")) {
                        account.close();
                    }
                }
                out.println(account.getBalance());
            } catch (IOException ex) {
                response.setStatus(500);
                // Can't remember the way to output to the logger...
            }
        }
    }
}
