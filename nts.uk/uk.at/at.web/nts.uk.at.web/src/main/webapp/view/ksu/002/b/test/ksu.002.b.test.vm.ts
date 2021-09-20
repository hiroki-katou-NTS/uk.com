module nts.uk.ui.at.ksu002.b.test {

    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {
        constructor() {
            super();
            const vm = this;
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
        }

        openKsu002b() {
            let vm = this;
            vm.$window.modal('/view/ksu/002/b/index.xhtml').then(() => {

            });
        }
    }
}