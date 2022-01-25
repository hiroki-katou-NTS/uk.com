import { _,Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
  name: 'kdls10',
  style: require('./style.scss'),
  template: require('./index.vue'),
  resource: require('./resources.json'),
  validations: {},
  constraints: [],
})
export class KDLS1OAComponent extends Vue {
  @Prop({
    default: () => ({
      isShowNoSelectRow: false,
      selectedCode: null,
    }),
  })
  public readonly params!: IParameter;

  public allData: Array<any> = [];
  public data: Array<any> = [];
  public activeNoSelect: boolean = true;
  public searchText: string = '';

  public created() {
    let self = this, $workLocation = [];

    self.$http.post('at', servicePath.getAllWorkLocation).then((result: any) => {
        if (_.isEmpty(result.data)) {
          //create data 選択なし with empty work place list
          if (self.params.isShowNoSelectRow) {
            $workLocation.push({
              workLocationCD: '',
              workLocationName: '選択なし',
              contractCode: '-1',
            });
          }
          
          self.allData = $workLocation;
          self.data = _.sortBy(self.allData, ['workLocationCD']);

          if (!_.isEmpty(self.params.selectedCode) || _.find(self.allData,(item) => item.workLocationCD == self.params.selectedCode) != undefined) {
            self.activeNoSelect = false;
          } else {
            self.activeNoSelect = true;
          }
        } else {
          //create data 選択なし and concatenate into array
          if (self.params.isShowNoSelectRow) {
            $workLocation.push({
              workLocationCD: '',
              workLocationName: '選択なし',
              contractCode: '-1',
            });
          }

          self.allData = [...$workLocation, ...result.data];
          self.data = _.sortBy(self.allData, ['workLocationCD']);

          if (!_.isEmpty(self.params.selectedCode) || _.find(self.allData,(item) => item.workLocationCD == self.params.selectedCode) != undefined) {
            self.activeNoSelect = false;
          } else {
            self.activeNoSelect = true;
          }
        }
        self.$mask('hide');
      })
      .catch((res: any) => {
        this.$modal.error(res.messageId).then(() => self.$close());
      });
  }

  public mounted() {
    this.$mask('show', { message: true });
  }

  public searchList() {
    this.data = _.filter(this.allData,(item) => item.workLocationCD.indexOf(this.searchText) != -1 || item.workLocationName.indexOf(this.searchText) != -1
    );
  }
}

const servicePath = {
  getAllWorkLocation: 'at/record/worklocation/findall',
};

interface IParameter {
  isShowNoSelectRow: boolean;
  selectedCode: string;
}
