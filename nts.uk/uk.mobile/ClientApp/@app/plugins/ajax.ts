import { $ } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

interface IFetchOption {
    url: string;
    type?: 'url' | 'form' | 'json';
    method: 'get' | 'post' | 'push' | 'patch' | 'delete';
    data?: any;
    headers?: any;
    pg?: 'at' | 'pr' | 'com';
    responseType?: 'blob' | 'arraybuffer' | 'document' | 'json' | 'text' | null;
}

const WEB_APP_NAME = {
    at: 'nts.uk.at.web',
    pr: 'nts.uk.pr.web',
    com: 'nts.uk.com.web'
}, ajax = {
    install(vue: VueConstructor<Vue>, prefixUrl: string) {
        const fetch = function (opt: IFetchOption) {
            return new Promise(function (resolve, reject) {
                if (!$.isObject(opt)) {
                    reject('No required parameters - "url" and "method".')
                    return;
                }

                if ($.isEmpty(opt.url)) {
                    reject('Parameter "url" is required.')
                    return;
                } else {
                    $.extend(opt, {
                        url: (`${process.env ? "http://localhost:8080" : ""}/${WEB_APP_NAME[opt.pg || 'com']}/${prefixUrl}/${opt.url}`).replace(/([^:]\/)\/+/g, "$1")
                    });
                }

                if ($.isEmpty(opt.method)) {
                    reject('Parameter "method" is required.')
                    return;
                }

                const xhr = new XMLHttpRequest(),
                    parseData = () => {
                        if (opt.type) {
                            switch (opt.type.toLowerCase()) {
                                case 'form':
                                    setHeaders({ 'Content-Type': 'multipart/form-data' });
                                    return Object.prototype.toString.call(opt.data) === '[object FormData]' ? opt.data : new FormData(opt.data);
                                case 'url':
                                    setHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
                                    return JSON.stringify(opt.data);
                                case 'json':
                                    setHeaders({ 'Content-Type': 'application/json' });
                                    return isJSON(opt.data) ? opt.data : JSON.stringify(opt.data);
                                default:
                                    return opt.data;
                            }
                        }
                    }, setHeaders = (headers: any) => {
                        $.objectForEach(headers, (header: string, value: any) => {
                            xhr.setRequestHeader(header, value);
                        });
                    }, isJSON = (json: string) => {
                        try {
                            JSON.parse(json)
                            return true;
                        }
                        catch (e) {
                            return false;
                        }
                    }, parseHeaders = (xhr: XMLHttpRequest) => {
                        return function () {
                            var raw = xhr.getAllResponseHeaders()

                            return headersParser(raw)
                        }
                    }, headersParser = (rawHeaders: string) => {
                        var headers: any = {};

                        if (!rawHeaders) {
                            return headers;
                        }

                        var headerPairs = rawHeaders.split('\u000d\u000a');

                        for (var i = 0; i < headerPairs.length; i++) {
                            var headerPair = headerPairs[i];
                            // Can't use split() here because it does the wrong thing
                            // if the header value has the string ": " in it.
                            var index = headerPair.indexOf('\u003a\u0020');

                            if (index > 0) {
                                let key = headerPair.substring(0, index),
                                    val = headerPair.substring(index + 2);

                                headers[key] = val;
                            }
                        }
                        return headers;
                    };

                if (opt.data) {
                    opt.data = parseData();
                }

                xhr.open(opt.method, opt.url, true);

                if (opt.headers) {
                    setHeaders(opt.headers);
                }

                // authentication 
                setHeaders({
                    'PG-Path': 'nts.uk.com.web',
                    //'X-CSRF-TOKEN': localStorage.getItem('csrf') || ''
                });

                if (opt.responseType) {
                    xhr.responseType = opt.responseType;
                }

                xhr.onerror = function () {
                    reject(xhr);
                };

                xhr.onload = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        try {
                            resolve({ data: JSON.parse(xhr.response), headers: parseHeaders(xhr) });
                        }
                        catch (e) {
                            resolve({ data: xhr.response, headers: parseHeaders(xhr) });
                        }

                    } else {
                        reject(xhr);
                    }
                };

                xhr.send(opt.data);
            });
        };

        // authentication
        vue.mixin({
            beforeMount() {
                if (!localStorage.getItem('csrf')) {
                    //this.$router.push({ path: '/access/login' });
                }
            }
        });

        vue.prototype.$http = {
            get: (pg: 'at' | 'com' | "pr" | string, url: string) => {
                if (pg === 'at' || pg === 'com' || pg === 'pr') {
                    return fetch({ pg, url, method: 'get' });
                } else {
                    return fetch({ pg: 'com', url: pg, method: 'get' });
                }
            },
            post: (pg: 'at' | 'com' | "pr" | string, url?: string | any, data?: any) => {
                if (pg === 'at' || pg === 'com' || pg === 'pr') {
                    return fetch({ pg, url, data, method: 'post' });
                } else {
                    return fetch({ pg: 'com', url: pg, data: url, method: 'post' });
                }
            },
            async: {
                info: (taskdId: string) => {
                    return fetch({
                        method: 'post',
                        url: '/ntscommons/arc/task/async/info/' + taskdId
                    });
                },
                cancel: (taskdId: string) => {
                    return fetch({
                        method: 'post',
                        url: '/ntscommons/arc/task/async/requesttocancel/' + taskdId
                    });
                }
            },
            file: {
                live: (fileId: string) => {

                },
                delete: (fileId: string) => {
                    return fetch({
                        method: 'post',
                        url: `/shr/infra/file/storage/delete/${fileId}`
                    });
                },
                upload: (form: FormData) => {
                    ///nts.uk.com.web/webapi/ntscommons/arc/filegate/upload
                },
                download: (fileId: string) => {
                    return fetch({
                        method: 'get',
                        responseType: 'blob',
                        url: `/shr/infra/file/storage/get/${fileId}`
                    }).then((resp: { data: any, headers: any }) => {
                        const blob = resp.data,
                            fileName = resp.headers.fileName,
                            link = document.createElement('a');

                        link.href = URL.createObjectURL(blob);
                        link.download = fileName;

                        link.click();
                    }).catch((reason: any) => {
                        console.log(reason);
                    });
                }
            },
            headers: {
                Authorization: ''
            }
        };
    }
};

export { ajax };