package minimvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

public abstract class Command {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected PrintWriter out;

    /**
     * Método interno usado por FrontController
     */
    final void init(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        this.out = new PrintResponseWrapper(response);
    }

    // Métodos utilitários

    protected final void forward(String path) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

    protected final void redirect(String location) throws IOException {
        response.sendRedirect(location);
    }

    protected final String contextUrl(String relativePath) {
        if (relativePath == null) {
            relativePath = "";
        }
        return request.getContextPath() + relativePath;
    }

    protected final String contextUrl() {
        return contextUrl(null);
    }

    protected final Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();

        for (Cookie ck : cookies) {
            if (ck.getName().equals(name)) {
                return ck;
            }
        }

        return null;
    }

    protected final void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    protected final String getParameter(String name) {
        return request.getParameter(name);
    }

    protected final Object getAttribute(String name) {
        return getAttribute(name, null);
    }

    protected final Object getAttribute(String name, String scope) {
        if (scope == null) {
            Object value = request.getAttribute(name);
            if (value != null) {
                return value;
            }

            value = request.getSession().getAttribute(name);
            if (value != null) {
                return value;
            }

            value = request.getServletContext().getAttribute(name);
            if (value != null) {
                return value;
            }

            return null;
        }

        switch (scope) {
            case "request":
                return request.getAttribute(name);

            case "session":
                return request.getSession().getAttribute(name);

            case "application":
                return request.getServletContext().getAttribute(name);

            default:
                throw new IllegalArgumentException("scope");
        }
    }

    protected final void setAttribute(String name, Object value) {
        setAttribute(name, value, null);
    }

    protected final void setAttribute(String name, Object value, String scope) {
        if (scope == null)
            scope = "request";

        switch (scope) {
            case "request":
                request.setAttribute(name, value);
                break;

            case "session":
                request.getSession().setAttribute(name, value);
                break;

            case "application":
                request.getServletContext().setAttribute(name, value);
                break;

            default:
                throw new IllegalArgumentException("scope");
        }
    }

    protected final void removeAttribute(String name) {
        removeAttribute(name, null);
    }

    protected final void removeAttribute(String name, String scope) {
        if (scope == null) {
            setAttribute(name, null, "request");
            setAttribute(name, null, "session");
            setAttribute(name, null, "application");
        } else {
            setAttribute(name, null, scope);
        }
    }

    private static class PrintResponseWrapper extends PrintWriter {
        static final StringWriter DUMMY = new StringWriter(0);

        HttpServletResponse response;

        PrintResponseWrapper(HttpServletResponse response) {
            super(DUMMY);
            this.response = response;
            this.out = null;
        }

        @Override
        public void flush() {
            synchronized (lock) {
                prepareWriter();
                super.flush();
            }
        }

        @Override
        public void close() {
            synchronized (lock) {
                prepareWriter();
                response = null;
                super.close();
            }
        }

        @Override
        public void write(int c) {
            synchronized (lock) {
                prepareWriter();
                super.write(c);
            }
        }

        @Override
        public void write(char[] buf, int off, int len) {
            synchronized (lock) {
                prepareWriter();
                super.write(buf, off, len);
            }
        }

        @Override
        public void write(String s, int off, int len) {
            synchronized (lock) {
                prepareWriter();
                super.write(s, off, len);
            }
        }

        @Override
        public void println() {
            synchronized (lock) {
                prepareWriter();
                super.println();
            }
        }

        @Override
        public PrintWriter format(String format, Object... args) {
            synchronized (lock) {
                prepareWriter();
                return super.format(format, args);
            }
        }

        @Override
        public PrintWriter format(Locale l, String format, Object... args) {
            synchronized (lock) {
                prepareWriter();
                return super.format(l, format, args);
            }
        }

        private void prepareWriter() {
            if (this.out == null && response != null) {
                try {
                    this.out = response.getWriter();
                } catch (IOException e) {
                    throw new IllegalStateException("Response writer not available");
                }
            }
        }
    }
}
