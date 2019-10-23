import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cdls04a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CdlS04AComponent extends Vue {
    
    @Prop({
        default: () => ({
            isShowNoSelectRow: false,
            selectedCode: null
        })
    })
    public readonly params!: IParameter;
    public allData: Array<any> = [];
    public data: Array<any> = [];
    public activeNoSelect: boolean = true;
    public searchText: string = '';

    public created() {
        let self = this;
        let param = { baseDate: new Date() };
        self.$mask('show');
        //起動時処理
        self.$http.post('com', servicePath.findJobTitle, param).then((result: any) => {
            self.$mask('hide');
            if (_.isEmpty(result.data)) {//TH không có data
                //エラーメッセージ（#Msg_1566）を表示する
                self.$modal.error({ messageId: 'Msg_1566', messageParams: ['Com_Jobtitle'] }).then(() => self.$close());
            } else {
                //Parameter．未選択表示をチェックする
                if (self.params.isShowNoSelectRow) {//true
                    //画面項目「職位一覧」の先頭に「選択なし」を追加する
                    result.data.push({
                        code: '',
                        name: this.$i18n('CDLS04_5')
                    });
                }
                self.allData = _.sortBy(result.data, ['code']);
                self.data = self.allData;

                if (_.find(self.allData, (item) => item.code == self.params.selectedCode) != undefined) {
                    self.activeNoSelect = false;
                } else {
                    self.activeNoSelect = true;
                }
            }
        }).catch((res: any) => {
            self.$mask('hide');
            self.$modal.error(res.messageId).then(() => self.$close());
        });

    }

    public searchList() {
        this.data = _.filter(this.allData, (item) => item.code.indexOf(this.searchText) != -1 || item.name.indexOf(this.searchText) != -1);
    }
}
interface IParameter {
    isShowNoSelectRow: boolean;
    selectedCode: string;
}
const servicePath = {
    findJobTitle: 'bs/employee/jobtitle/findAll'
};