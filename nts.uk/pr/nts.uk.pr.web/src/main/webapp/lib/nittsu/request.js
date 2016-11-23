var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var request;
        (function (request) {
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
            function ajax(path, data, options) {
                var dfd = $.Deferred();
                options = options || {};
                if (typeof data === 'object') {
                    data = JSON.stringify(data);
                }
                var webserviceLocator = location.ajaxRoot.mergeRelativePath(path);
                $.ajax({
                    type: options.method || 'POST',
                    contentType: options.contentType || 'application/json',
                    url: webserviceLocator.serialize(),
                    dataType: options.dataType || 'json',
                    data: data
                }).done(function (res) {
                    dfd.resolve(res);
                });
                return dfd.promise();
            }
            request.ajax = ajax;
            function jump(path) {
                var destination;
                if (path.charAt(0) === '/') {
                    destination = location.appRoot.mergeRelativePath(path);
                }
                else {
                    destination = location.current.mergeRelativePath(path);
                }
                window.location.href = destination.rawUrl;
            }
            request.jump = jump;
            var location;
            (function (location) {
                location.current = new Locator(window.location.href);
                location.appRoot = location.current.mergeRelativePath(__viewContext.rootPath);
                location.ajaxRoot = location.appRoot.mergeRelativePath('webapi/');
            })(location = request.location || (request.location = {}));
            ;
        })(request = uk.request || (uk.request = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
