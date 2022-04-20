import os, sys, re, pickle, glob
import urllib.request
import zipfile
import urllib.request 
import xml.etree.ElementTree as ET
from multiprocessing import Pool
import pandas as pd
from os import listdir 
import os

def getCorpusData(file):
    clinicalData = []
    #for file in files:
    #print(file)
    try:
        tree = ET.parse(file)
        root = tree.getroot()
        for tag in root.iter('article-id'):
            if(tag.attrib.get('pub-id-type') == 'pmc'):
                pmcId = ((file.split('\\')[7]).replace('.nxml', '')) if tag.text is None else tag.text

        '''for tag in root.iter('journal-id'):
        journalId = tag.text'''

        for tag in root.iter('journal-title'):
            journalTitle =tag.text

        data = ''
        for tag in root.iter('p'):
            data += '' if tag.text is None else tag.text

        return (pmcId, journalTitle, data)
    except:
        return ''

if __name__ == '__main__':

    storageLink = 'D:\\1.Course_Material\\IR\\searchEngine\\data\\pmc-text-00'
    #storageLink = 'D:\\1.Course_Material\\IR\\searchEngine\\data\\pmc-text-01'
    #storageLink = 'D:\\1.Course_Material\\IR\\searchEngine\\data\\pmc-text-02'
    #storageLink = 'D:\\1.Course_Material\\IR\\searchEngine\\data\\pmc-text-03'
    corpusData = []

    print(storageLink)
    for folder in os.listdir(storageLink):
        link = storageLink+'\\'+folder
        print('link:--',link)
        files = glob.glob(link+'\\**\*.nxml', recursive=True)
        p = Pool(50)
        daas = p.map(getCorpusData, files)
        corpusData += daas
        p.terminate()
        p.close()
        print('corpusData:--',len(corpusData))

    df = pd.DataFrame(corpusData, columns=['PMC_ID', 'JOURNAL_ID', 'DETAILS'] )
    print('df done')
    df.to_parquet(storageLink+'.parquet')
    print('corpusData file done:')
    print('PROCESS COMPLETE')
    sys.exit()
    exit()
