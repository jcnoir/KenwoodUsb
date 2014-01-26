KenwoodUsb
==========

Copy music files from a folder to an other one (USB key ...) with the Kenwood limitations on folder number, file number ...

The kenwood limitations can be found here :
http://www.kenwood.com/cs/ce/audiofile/index.php?model=DNX7230DAB&lang=english

Build with : gradle install

Launch the command line interface with : 
./build/install/KenwoodUsb/bin/KenwoodUsb -source source_folder -target target_folder

The target folder will be like this : 
Copy : test_music_file_999.mp3 --> KUSB000/339015533.mp3
