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
                return Locator;
            }());
            request.Locator = Locator;
        })(request = uk.request || (uk.request = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
