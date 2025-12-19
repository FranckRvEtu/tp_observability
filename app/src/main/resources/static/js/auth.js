/**
 * Gestion de l'authentification et des headers X-User-Id
 * Ce script :
 * 1. Stocke l'userId dans le localStorage après connexion
 * 2. Ajoute le header X-User-Id à toutes les requêtes HTTP (fetch)
 * 3. Fournit des méthodes utiles pour l'auth
 */

const Auth = {
    /**
     * Récupère l'userId depuis le localStorage
     */
    getUserId() {
        try {
            const user = JSON.parse(localStorage.getItem('user') || '{}');
            return user.id || null;
        } catch (e) {
            console.error('Erreur lors du parsing du user:', e);
            return null;
        }
    },

    /**
     * Stocke l'utilisateur (après connexion/inscription)
     */
    setUser(userData) {
        try {
            localStorage.setItem('user', JSON.stringify(userData));
        } catch (e) {
            console.error('Erreur lors du stockage de l\'user:', e);
        }
    },

    /**
     * Récupère l'utilisateur complet
     */
    getUser() {
        try {
            return JSON.parse(localStorage.getItem('user') || 'null');
        } catch (e) {
            console.error('Erreur lors du parsing du user:', e);
            return null;
        }
    },

    /**
     * Supprime l'utilisateur (déconnexion)
     */
    clearUser() {
        localStorage.removeItem('user');
    },

    /**
     * Enveloppe la fonction fetch pour ajouter le header X-User-Id
     */
    fetch(url, options = {}) {
        const userId = this.getUserId();
        const headers = options.headers || {};

        // Ajouter le header X-User-Id s'il existe
        if (userId) {
            headers['X-User-Id'] = userId;
        }

        const finalOptions = {
            ...options,
            headers
        };

        return fetch(url, finalOptions);
    },

    /**
     * Vérifier si l'utilisateur est connecté
     */
    isLoggedIn() {
        return this.getUserId() !== null;
    },

    /**
     * Rediriger vers la page de connexion si non authentifié
     */
    requireAuth() {
        if (!this.isLoggedIn()) {
            window.location.href = '/login';
            return false;
        }
        return true;
    }
};

// Optionnel : Intercepter toutes les requêtes fetch globalement (approche alternative)
// Cette fonction remplace la fetch native et ajoute automatiquement le header
(function() {
    const originalFetch = window.fetch;
    window.fetch = function(...args) {
        const [resource, config] = args;
        const userId = Auth.getUserId();

        if (userId) {
            // Initialiser les headers s'ils n'existent pas
            if (!config) {
                args[1] = { headers: { 'X-User-Id': userId } };
            } else if (!config.headers) {
                config.headers = { 'X-User-Id': userId };
            } else if (typeof config.headers === 'object') {
                config.headers['X-User-Id'] = userId;
            }
        }

        return originalFetch.apply(this, args);
    };
})();

