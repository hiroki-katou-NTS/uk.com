import { Vue } from '@app/provider';

// xử lý chuyển đổi một giá trị của time từ number sang string tại đây
Vue.filter('time', (value: number) => value);

// xử lý chuyển đổi một giá trị của time từ number sang string tại đây
Vue.filter('timewd', (value: number) => value);