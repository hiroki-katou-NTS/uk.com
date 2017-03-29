<<<<<<< HEAD
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var request;
        (function (request) {
            request.STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";
            var WEB_APP_NAME = {
                com: 'nts.uk.com.web',
                pr: 'nts.uk.pr.web'
            };
            var QueryString = (function () {
                function QueryString() {
                    this.items = {};
                }
                QueryString.parseUrl = function (url) {
                    var instance = new QueryString();
                    var queryString = url.split('?')[1];
                    if (queryString) {
                        var queryEntries = queryString.split('&');
                        for (var i = 0; i < queryEntries.length; i++) {
                            var entryParts = queryEntries[i].split('=');
                            instance.set(entryParts[0], entryParts[1]);
                        }
                    }
                    return instance;
                };
                QueryString.build = function (entriesObj) {
                    var instance = new QueryString();
                    for (var key in entriesObj) {
                        instance.set(key, entriesObj[key]);
                    }
                    return instance;
                };
                QueryString.prototype.get = function (key) {
                    return this.items[key];
                };
                QueryString.prototype.set = function (key, value) {
                    if (key === null || key === undefined || key === '') {
                        return;
                    }
                    this.items[key] = value;
                };
                QueryString.prototype.remove = function (key) {
                    delete this.items[key];
                };
                QueryString.prototype.mergeFrom = function (otherObj) {
                    for (var otherKey in otherObj.items) {
                        this.set(otherKey, otherObj.items[otherKey]);
                    }
                };
                QueryString.prototype.count = function () {
                    var count = 0;
                    for (var key in this.items) {
                        count++;
                    }
                    return count;
                };
                QueryString.prototype.hasItems = function () {
                    return this.count() !== 0;
                };
                QueryString.prototype.serialize = function () {
                    var entryStrings = [];
                    for (var key in this.items) {
                        entryStrings.push(key + '=' + this.items[key]);
                    }
                    return entryStrings.join('&');
                };
                return QueryString;
            }());
            request.QueryString = QueryString;
            /**
             * URL and QueryString
             */
            var Locator = (function () {
                function Locator(url) {
                    this.rawUrl = url;
                    this.queryString = QueryString.parseUrl(url);
                }
                Locator.prototype.serialize = function () {
                    if (this.queryString.hasItems()) {
                        return this.rawUrl + '?' + this.queryString.serialize();
                    }
                    else {
                        return this.rawUrl;
                    }
                };
                Locator.prototype.mergeRelativePath = function (relativePath) {
                    var stack = this.rawUrl.split('/');
                    var parts = relativePath.split('/');
                    var queryStringToAdd = QueryString.parseUrl(relativePath);
                    // 最後のファイル名は除外
                    // (最後がフォルダ名でしかも / で終わっていない場合は考慮しない)
                    stack.pop();
                    // relativePathの先頭が '/' の場合、それを取り除く
                    if (parts[0] === '') {
                        parts.shift();
                    }
                    for (var i = 0; i < parts.length; i++) {
                        if (parts[i] === '.')
                            continue;
                        if (parts[i] === '..')
                            stack.pop();
                        else
                            stack.push(parts[i]);
                    }
                    queryStringToAdd.mergeFrom(this.queryString);
                    var queryStringParts = queryStringToAdd.hasItems()
                        ? '?' + queryStringToAdd.serialize()
                        : '';
                    return new Locator(stack.join('/') + queryStringParts);
                };
                return Locator;
            }());
            request.Locator = Locator;
            function ajax(webAppId, path, data, options) {
                if (typeof arguments[1] !== 'string') {
                    return ajax.apply(null, _.concat(location.currentAppId, arguments));
                }
                var dfd = $.Deferred();
                options = options || {};
                if (typeof data === 'object') {
                    data = JSON.stringify(data);
                }
                var webserviceLocator = location.siteRoot
                    .mergeRelativePath(WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(location.ajaxRootDir)
                    .mergeRelativePath(path);
                $.ajax({
                    type: options.method || 'POST',
                    contentType: options.contentType || 'application/json',
                    url: webserviceLocator.serialize(),
                    dataType: options.dataType || 'json',
                    data: data
                }).done(function (res) {
                    if (res !== undefined && res.businessException) {
                        dfd.reject(res);
                    }
                    else {
                        dfd.resolve(res);
                    }
                });
                return dfd.promise();
            }
            request.ajax = ajax;
            function exportFile(path, data, options) {
                var dfd = $.Deferred();
                ajax(path, data, options)
                    .then(function (res) {
                    return uk.deferred.repeat(function (conf) { return conf
                        .task(function () { return specials.getAsyncTaskInfo(res.taskId); })
                        .while(function (info) { return info.pending || info.running; })
                        .pause(1000); });
                })
                    .done(function (res) {
                    specials.donwloadFile(res.id);
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            request.exportFile = exportFile;
            var specials;
            (function (specials) {
                function getAsyncTaskInfo(taskId) {
                    return ajax('/ntscommons/arc/task/async/' + taskId);
                }
                specials.getAsyncTaskInfo = getAsyncTaskInfo;
                function donwloadFile(fileId) {
                    $('<iframe/>')
                        .attr('id', 'download-frame')
                        .appendTo('body')
                        .attr('src', resolvePath('/webapi/ntscommons/arc/filegate/get/' + fileId));
                }
                specials.donwloadFile = donwloadFile;
            })(specials = request.specials || (request.specials = {}));
            function jump(path, data) {
                uk.sessionStorage.setItemAsJson(request.STORAGE_KEY_TRANSFER_DATA, data);
                window.location.href = resolvePath(path);
            }
            request.jump = jump;
            function resolvePath(path) {
                var destination;
                if (path.charAt(0) === '/') {
                    destination = location.appRoot.mergeRelativePath(path);
                }
                else {
                    destination = location.current.mergeRelativePath(path);
                }
                return destination.rawUrl;
            }
            request.resolvePath = resolvePath;
            var location;
            (function (location) {
                location.current = new Locator(window.location.href);
                location.appRoot = location.current.mergeRelativePath(__viewContext.rootPath);
                location.siteRoot = location.appRoot.mergeRelativePath('../');
                location.ajaxRootDir = 'webapi/';
                var currentAppName = _.takeRight(location.appRoot.serialize().split('/'), 2)[0];
                for (var id in WEB_APP_NAME) {
                    if (currentAppName === WEB_APP_NAME[id]) {
                        location.currentAppId = id;
                        break;
                    }
                }
            })(location = request.location || (request.location = {}));
            ;
        })(request = uk.request || (uk.request = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
=======
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var request;
        (function (request) {
            request.STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";
            var WEB_APP_NAME = {
                com: 'nts.uk.com.web',
                pr: 'nts.uk.pr.web'
            };
            var QueryString = (function () {
                function QueryString() {
                    this.items = {};
                }
                QueryString.parseUrl = function (url) {
                    var instance = new QueryString();
                    var queryString = url.split('?')[1];
                    if (queryString) {
                        var queryEntries = queryString.split('&');
                        for (var i = 0; i < queryEntries.length; i++) {
                            var entryParts = queryEntries[i].split('=');
                            instance.set(entryParts[0], entryParts[1]);
                        }
                    }
                    return instance;
                };
                QueryString.build = function (entriesObj) {
                    var instance = new QueryString();
                    for (var key in entriesObj) {
                        instance.set(key, entriesObj[key]);
                    }
                    return instance;
                };
                QueryString.prototype.get = function (key) {
                    return this.items[key];
                };
                QueryString.prototype.set = function (key, value) {
                    if (key === null || key === undefined || key === '') {
                        return;
                    }
                    this.items[key] = value;
                };
                QueryString.prototype.remove = function (key) {
                    delete this.items[key];
                };
                QueryString.prototype.mergeFrom = function (otherObj) {
                    for (var otherKey in otherObj.items) {
                        this.set(otherKey, otherObj.items[otherKey]);
                    }
                };
                QueryString.prototype.count = function () {
                    var count = 0;
                    for (var key in this.items) {
                        count++;
                    }
                    return count;
                };
                QueryString.prototype.hasItems = function () {
                    return this.count() !== 0;
                };
                QueryString.prototype.serialize = function () {
                    var entryStrings = [];
                    for (var key in this.items) {
                        entryStrings.push(key + '=' + this.items[key]);
                    }
                    return entryStrings.join('&');
                };
                return QueryString;
            }());
            request.QueryString = QueryString;
            var Locator = (function () {
                function Locator(url) {
                    this.rawUrl = url;
                    this.queryString = QueryString.parseUrl(url);
                }
                Locator.prototype.serialize = function () {
                    if (this.queryString.hasItems()) {
                        return this.rawUrl + '?' + this.queryString.serialize();
                    }
                    else {
                        return this.rawUrl;
                    }
                };
                Locator.prototype.mergeRelativePath = function (relativePath) {
                    var stack = this.rawUrl.split('/');
                    var parts = relativePath.split('/');
                    var queryStringToAdd = QueryString.parseUrl(relativePath);
                    stack.pop();
                    if (parts[0] === '') {
                        parts.shift();
                    }
                    for (var i = 0; i < parts.length; i++) {
                        if (parts[i] === '.')
                            continue;
                        if (parts[i] === '..')
                            stack.pop();
                        else
                            stack.push(parts[i]);
                    }
                    queryStringToAdd.mergeFrom(this.queryString);
                    var queryStringParts = queryStringToAdd.hasItems()
                        ? '?' + queryStringToAdd.serialize()
                        : '';
                    return new Locator(stack.join('/') + queryStringParts);
                };
                return Locator;
            }());
            request.Locator = Locator;
            function ajax(webAppId, path, data, options) {
                if (typeof arguments[1] !== 'string') {
                    return ajax.apply(null, _.concat(location.currentAppId, arguments));
                }
                var dfd = $.Deferred();
                options = options || {};
                if (typeof data === 'object') {
                    data = JSON.stringify(data);
                }
                var webserviceLocator = location.siteRoot
                    .mergeRelativePath(WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(location.ajaxRootDir)
                    .mergeRelativePath(path);
                $.ajax({
                    type: options.method || 'POST',
                    contentType: options.contentType || 'application/json',
                    url: webserviceLocator.serialize(),
                    dataType: options.dataType || 'json',
                    data: data
                }).done(function (res) {
                    if (res !== undefined && res.businessException) {
                        dfd.reject(res);
                    }
                    else {
                        dfd.resolve(res);
                    }
                });
                return dfd.promise();
            }
            request.ajax = ajax;
            function exportFile(path, data, options) {
                var dfd = $.Deferred();
                ajax(path, data, options)
                    .then(function (res) {
                    return uk.deferred.repeat(function (conf) { return conf
                        .task(function () { return specials.getAsyncTaskInfo(res.taskId); })
                        .while(function (info) { return info.pending || info.running; })
                        .pause(1000); });
                })
                    .done(function (res) {
                    specials.donwloadFile(res.id);
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            request.exportFile = exportFile;
            var specials;
            (function (specials) {
                function getAsyncTaskInfo(taskId) {
                    return ajax('/ntscommons/arc/task/async/' + taskId);
                }
                specials.getAsyncTaskInfo = getAsyncTaskInfo;
                function donwloadFile(fileId) {
                    $('<iframe/>')
                        .attr('id', 'download-frame')
                        .appendTo('body')
                        .attr('src', resolvePath('/webapi/ntscommons/arc/filegate/get/' + fileId));
                }
                specials.donwloadFile = donwloadFile;
            })(specials = request.specials || (request.specials = {}));
            function jump(path, data) {
                uk.sessionStorage.setItemAsJson(request.STORAGE_KEY_TRANSFER_DATA, data);
                window.location.href = resolvePath(path);
            }
            request.jump = jump;
            function resolvePath(path) {
                var destination;
                if (path.charAt(0) === '/') {
                    destination = location.appRoot.mergeRelativePath(path);
                }
                else {
                    destination = location.current.mergeRelativePath(path);
                }
                return destination.rawUrl;
            }
            request.resolvePath = resolvePath;
            var location;
            (function (location) {
                location.current = new Locator(window.location.href);
                location.appRoot = location.current.mergeRelativePath(__viewContext.rootPath);
                location.siteRoot = location.appRoot.mergeRelativePath('../');
                location.ajaxRootDir = 'webapi/';
                var currentAppName = _.takeRight(location.appRoot.serialize().split('/'), 2)[0];
                for (var id in WEB_APP_NAME) {
                    if (currentAppName === WEB_APP_NAME[id]) {
                        location.currentAppId = id;
                        break;
                    }
                }
            })(location = request.location || (request.location = {}));
            ;
        })(request = uk.request || (uk.request = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
>>>>>>> basic/develop
//# sourceMappingURL=request.js.map