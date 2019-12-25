module jcm007.a {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    var block = nts.uk.ui.block;

    export class ViewModel {
       
        constructor() {
            let self = this;
            $(".employee-info-pop-up").ntsPopup("hide");
        }

        /** start page */
        start() {
            let self = this;
            block.grayout();
            let dfd = $.Deferred<any>();
            service.getData().done((data : IStartPageDto) => {
                console.log(data);
                dfd.resolve();
                // block.clear();
            }).fail((error) => {
                console.log('Get Data Fail');
                dfd.reject();
                dialog.info(error);
            }).always(() => {
                block.clear();
            });
            
            return dfd.promise();
        }

        /** event when click register */
        public showModal(id, key, el) {
            // $('.nts-grid-control-' + key + '-' + id).append("<b>Appended text</b>");
            let self = this;
            console.log(key);
            console.log(id);
            let emp = {
                code: id,
                name: '社員名 員名',
                kanaName: '社員名 ',
                sex:'性別',
                dob:'1991/04/11',
                age:'29分',
                department:'部門 学科 局 部科売り場',
                position: '職位職位',
                employment:'雇用雇用',
                image: 'https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/67660458_2163133103815091_6832643697729863680_o.jpg?_nc_cat=108&_nc_ohc=PzzrUkG6zBAAQkMqXJeoGd7dj9YkJUgyqnSPGDqzbcJ2uPPb_DzzpiSRw&_nc_ht=scontent.fhan2-3.fna&oh=51b9cc5985e9e57dcae281e966c62f20&oe=5E8674C7'
            };

            $(".employee-info-pop-up").ntsPopup({
                trigger: '.nts-grid-control-' + key + '-' + id,
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: '.nts-grid-control-' + key + '-' + id
                },
                showOnStart: true,
                dismissible: true
            });
            // Toggle
            
        };
    }

}

