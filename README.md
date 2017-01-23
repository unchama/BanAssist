# 免責事項
当プラグインでは、当プラグインの開発元ではない外部団体であるJapan Minecraft PvP(以下、JPMCPvP)が公開しているCompromised Accountによる処罰データを使用しています。当プラグインを使用して発生した障害や問題に関して、当プラグインの提供者及び処罰データの提供元であるJPMCPvPは一切の責任を負いません。当プラグイン導入の際には、利用者の自己の責任の元ご利用ください。<br>
<br>
# BanAssist
config.ymlで指定したjsonファイルからcompromised accountのみを抽出し、アクセスを禁止するプラグイン。<br>
初期設定ではJPMCPvPが公開している処罰リストをお借りしています。<br>
誤検知を考慮して、Compromised accountであっても除外する機能を用意しています。<br>
/ignoreコマンドによりプレイヤー名を追加すると、プレイヤー名が未確定除外リストに登録されます。<br>
以降の該当プレイヤーの初回ログインによりUUIDを登録し、除外を確定します。<br>
jsonファイルからの読み込みは起動時のみ行っておりますので、自動再起動プラグインとの併用がお勧めです。<br>
<br>
# コマンドリファレンス
※本コマンドはコンソールまたはオペレータ権限を持つプレイヤーからのみ実行可能です。<br>
未確定除外リストにプレイヤーを追加：/ignore add [name]<br>
未確定除外リストからプレイヤーを削除：/ignore rem [name]<br>
未確定除外リストに登録されている全プレイヤーを列挙：/ignore list<br>
対象プレイヤーが未確定除外リストにあるかどうか確認：/ignore find [name]<br>
config.ymlをリロード（除外リスト手動更新用）：/ignore reload<br>
<br>
# 動作環境
spigot-1.10.2にて動作確認済みです。他の環境で動くか分かりませんが、まぁたぶん動くでしょう(適当)。<br>
<br>
# Download
ver0.1.3 2017/01/10<br>
https://www.dropbox.com/s/s680ogoyndczqxx/BanAssist.jar?dl=0<br>
※java1.8環境でコンパイルしたものです。1.7以前の環境で動作させる場合はソースコードからコンパイルし直せば使える筈。<br>
<br>
# SpecialThanks
Compromised Accountの判定にJPMCPvPの処罰リストを有難く利用させて頂いております。<br>
この場をお借りして、感謝申し上げます。
