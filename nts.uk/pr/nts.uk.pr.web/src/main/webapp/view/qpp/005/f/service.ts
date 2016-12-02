module nts.uk.pr.view.qpp005.f {
    export module service {
        var servicePath = {
            getCommute: "pr/proto/commute/findCommute",
            getCommuteNotaxLimit: "pr/proto/paymentdata/findCommuteNotaxLimit",
        };

        export function getCommute(personId: string, startYearmonth: number): any {
            var dfd = $.Deferred();
            var _path = nts.uk.text.format(servicePath.getCommute, personId, startYearmonth);

            nts.uk.request.ajax(_path).done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }
        
        export function getCommuteNotaxLimit(commuNotaxLimitCode: string): any {
            var dfd = $.Deferred();
            var _path = nts.uk.text.format(servicePath.getCommuteNotaxLimit, commuNotaxLimitCode);

            nts.uk.request.ajax(_path).done(function(res) {
                dfd.resolve(res);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }
    }
}