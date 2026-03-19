/* ===== Toast Notification ===== */
(function () {
    // Create toast container on DOM ready
    var containerEl = null;

    function getContainer() {
        if (!containerEl) {
            containerEl = document.createElement('div');
            containerEl.className = 'managa-toast-container';
            document.body.appendChild(containerEl);
        }
        return containerEl;
    }

    /**
     * Show a toast notification
     * @param {string} message - The message to display
     * @param {string} type - 'success' | 'error' | 'info' | 'warning'
     * @param {number} duration - Duration in ms (default 3000)
     */
    window.showToast = function (message, type, duration) {
        type = type || 'info';
        duration = duration || 3000;

        var toast = document.createElement('div');
        toast.className = 'managa-toast ' + type;
        toast.textContent = message;
        getContainer().appendChild(toast);

        // Trigger show animation
        requestAnimationFrame(function () {
            toast.classList.add('show');
        });

        setTimeout(function () {
            toast.classList.remove('show');
            setTimeout(function () {
                if (toast.parentNode) toast.parentNode.removeChild(toast);
            }, 300);
        }, duration);
    };
})();

/* ===== Confirm Dialog ===== */
/**
 * Show a confirm dialog (returns a Promise)
 * @param {string} message - Confirm message
 * @returns {Promise<boolean>}
 */
window.showConfirm = function (message) {
    return new Promise(function (resolve) {
        var overlay = document.createElement('div');
        overlay.className = 'managa-confirm-overlay active';

        var box = document.createElement('div');
        box.className = 'managa-confirm-box';

        var p = document.createElement('p');
        p.textContent = message;

        var buttons = document.createElement('div');
        buttons.className = 'confirm-buttons';

        var btnOk = document.createElement('button');
        btnOk.className = 'btn-managa-primary';
        btnOk.textContent = '확인';

        var btnCancel = document.createElement('button');
        btnCancel.className = 'btn-managa-danger';
        btnCancel.textContent = '취소';

        buttons.appendChild(btnOk);
        buttons.appendChild(btnCancel);
        box.appendChild(p);
        box.appendChild(buttons);
        overlay.appendChild(box);
        document.body.appendChild(overlay);

        function cleanup(result) {
            document.body.removeChild(overlay);
            resolve(result);
        }

        btnOk.addEventListener('click', function () { cleanup(true); });
        btnCancel.addEventListener('click', function () { cleanup(false); });
        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) cleanup(false);
        });
    });
};

/* ===== Loading Overlay ===== */
(function () {
    var loadingEl = null;

    function getLoading() {
        if (!loadingEl) {
            loadingEl = document.createElement('div');
            loadingEl.className = 'loading-overlay';
            loadingEl.innerHTML = '<div class="loading-spinner"></div>';
            document.body.appendChild(loadingEl);
        }
        return loadingEl;
    }

    window.showLoading = function () {
        getLoading().classList.add('active');
    };

    window.hideLoading = function () {
        getLoading().classList.remove('active');
    };
})();
