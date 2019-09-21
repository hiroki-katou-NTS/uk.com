<template>
<div class="kdws03f p-0">
    <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0 py-1">
    <div class="row uk-text-quote ml-0 mr-0 w-100">
      <!-- F1_1: 戻るボタン -->
      <div class="col-1 pl-1" v-on:click="$close()"><i class="fas fa-arrow-left"></i></div>
      <!-- F1_2: 対象社員名 -->
      <div class="col-5 pl-0 pr-2"><span>{{ empName }}</span></div>
      <!-- F1_3: 対象年月 -->
      <div class="col-6 pl-0 pr-3 text-right"><span>{{ date  | yearmonth('YYYY年MM月')}}</span></div>
    </div>
  </div>
    <!-- F2_1: 月別実績一覧 -->
    <table class="table table-bordered pt-0 mb-0">
    <tbody>
      <tr v-for="month in monthData" v-bind:key="month.id" :value="month">
        <!-- F2_2: 勤怠項目名 -->
        <td class="pl-2 pr-0 uk-bg-green">{{ month.name}}</td>
        <!-- F2_3: 勤怠項目の値 -->
        <td class="text-right pr-3">{{ month.value}}</td>
      </tr>
    </tbody>
  </table>
  <!-- F3_2: 実績無メッセージ -->
  <div v-if="dataRes" class="p-3">{{ 'KDWS03_74' | i18n }}</div>
</div>
</template>