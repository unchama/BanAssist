# 免責事項
当プラグインでは、当プラグインの開発元ではない外部団体であるJapan Minecraft PvP(以下、JPMCPvP)が公開しているCompromised Accountによる処罰データを使用しています。当プラグインを使用して発生した障害や問題に関して、当プラグインの提供者及び処罰データの提供元であるJPMCPvPは一切の責任を負いません。当プラグイン導入の際には、利用者の自己の責任の元ご利用ください。<br>
<br>
# BanAssist
config.ymlで指定したjsonファイルからcompromised accountのみを抽出し、アクセスを禁止するプラグイン。<br>
初期設定ではJPMCPvPが公開している処罰リストをお借りしています。<br>
誤検知も考慮して、/ignoreコマンドでプレイヤーを追加するとCompromised accountであっても除外処理されます。<br>
判定リストの更新は起動時のみ行っておりますので、自動再起動プラグインとの併用がお勧めです。<br>
<br>
# コマンドリファレンス
除外リストにプレイヤーを追加：/ignore add [name]<br>
除外リストからプレイヤーを削除：/ignore rem [name]<br>
除外リストに登録されている全プレイヤーを列挙：/ignore list<br>
対象プレイヤーが除外リストにあるかどうか確認：/ignore find [name]<br>
<br>
# 動作環境
spigot-1.10.2にて動作確認済みです。他の環境で動くか分かりませんが、まぁたぶん動くでしょう(適当)。<br>
<br>
# Download
ver0.1.2 2017/01/10<br>
https://www.dropbox.com/s/s680ogoyndczqxx/BanAssist.jar?dl=0<br>
<br>
# SpecialThanks
当リポジトリは、CrossHearts氏が作成したコードをご厚意でunchamaが譲り受けたものです。<br>
また、Compromised Accountの判定にJPMCPvPの処罰リストを有難く利用させて頂いております。<br>
この場をお借りして、感謝申し上げます。
