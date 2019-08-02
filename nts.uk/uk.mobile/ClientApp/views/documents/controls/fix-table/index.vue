<template>
  <div class="documentscontrolsfix-table">
    <h5>1. {{'sample' | i18n}}</h5>

        <span>{{ 'fix-table' | i18n}}</span>

    <fix-table table-class="table table-bordered table-sm m-0 border border-dark" class="mx-n2" style="font-size: 13px">
      <thead class="uk-bg-headline" style="height: 46px">
        <tr>
          <th></th>
          <th>勤務種類</th>
          <th style="font-size: 12px">就業時間帯</th>
          <th class="uk-bg-orange">出勤時刻</th>
          <th class="uk-bg-orange">退勤時刻</th>
          <th style="font-size: 10px">休憩開始時刻1</th>
          <th style="font-size: 10px">休憩終了時刻1</th>
          <th></th>
        </tr>
      </thead>
      <tbody >
        <tr v-for="row in rows" v-bind:key="row">
          <td style="width: 67px">{{row}}日(火)</td>
          <td>出勤</td>
          <td style="font-size: 8px">通常勤務</td>
          <td>8:30</td>
          <td>17:30</td>
          <td>12:00</td>
          <td>13:00</td>
          <td style="width: 52px"><span style="color: red" class="fa fa-exclamation-circle fa-lg"></span><span class="pl-1" v-on:click="openB">></span></td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <td>合計</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
      </tfoot>
    </fix-table>

    <span>{{'nature-table' | i18n }}</span>

    <table class="table table-bordered table-sm m-0 border border-dark mx-n2" style="font-size: 13px">
      <thead>
        <tr style="height: 55px">
          <th></th>
          <th>勤務種類</th>
          <th style="font-size: 12px">就業時間帯</th>
          <th>出勤時刻</th>
          <th>退勤時刻</th>
          <th style="font-size: 10px">休憩開始時刻1</th>
          <th style="font-size: 10px">休憩終了時刻1</th>
          <th></th>
        </tr>
      </thead>
      <tbody >
        <tr v-for="row in rows" v-bind:key="row">
          <td style="width: 67px">{{row}}日(火)</td>
          <td>出勤</td>
          <td style="font-size: 8px">通常勤務</td>
          <td>8:30</td>
          <td>17:30</td>
          <td>12:00</td>
          <td>13:00</td>
          <td style="width: 52px"><span style="color: red" class="fa fa-exclamation-circle fa-lg"></span><span class="pl-1" v-on:click="openB">></span></td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <td>合計</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
      </tfoot>
    </table>


    <markdown />
  </div>
</template>