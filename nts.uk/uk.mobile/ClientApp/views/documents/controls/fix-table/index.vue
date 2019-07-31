<template>
  <div class="documentscontrolsfix-table">
    <h5>1. {{'sample' | i18n}}</h5>
    <fix-table>
      <thead>
        <tr style="height: 55px">
          <th></th>
          <th>勤務種類</th>
          <th>就業時間帯</th>
          <th>出勤時刻</th>
          <th>退勤時刻</th>
          <th>休憩開始時刻1</th>
          <th>休憩終了時刻1</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in rows" v-bind:key="row">
          <td style="width: 70px">{{row}}日(火)</td>
          <td>出勤</td>
          <td>通常勤務</td>
          <td>8:30</td>
          <td>17:30</td>
          <td>12:00</td>
          <td>13:00</td>
          <td style="width: 52px"><button v-on:click="openB">></button></td>
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
    <markdown />
  </div>
</template>